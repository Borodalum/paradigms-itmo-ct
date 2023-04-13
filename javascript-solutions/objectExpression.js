"use strict"

const expPrototype = {
    evaluate(...args) {
        return this.operation.apply(null,
            this.expressions.map((expr) => expr.evaluate(...args))
        );
    },
    toString() {
        return this.expressions.map((expr) => expr.toString()).join(" ") + " " + this.sign;
    },
    prefix() {
        return "(" + this.sign + " " + this.expressions.map((expr) => expr.prefix()).join(" ") + ")";
    }
}

const SUPPORTEDOPER = {
};

function createOperation(sign, operation) {
    const fnc = function (...expressions) {
        this.expressions = expressions;
    }
    fnc.prototype = Object.create(expPrototype);
    fnc.prototype.sign = sign;
    fnc.prototype.operation = operation;

    SUPPORTEDOPER[sign] = [fnc, operation.length];
    return fnc;
}

const Subtract = createOperation("-", (x, y) => x - y);
const Add = createOperation("+", (x, y) => x + y);
const Multiply = createOperation("*", (x, y) => x * y);
const Divide = createOperation("/", (x, y) => x / y);
const Negate = createOperation("negate", (x) => -x);
const Exp = createOperation("exp", Math.exp);
const Ln = createOperation("ln", Math.log);
const Sum = createOperation("sum", (...args) => args.reduce((acc, cur) => acc + cur, 0));
const Avg = createOperation("avg", (...args) => args.reduce((acc, cur) => acc + cur, 0) / args.length);

function Variable(vrb) {
    // this.
    return {
        vrb: vrb,
        evaluate(...args) {
            return args[VARS[vrb]];
        },
        toString() {
            return this.vrb;
        },
        prefix(){
            return this.vrb;
        }
    }
}

new Variable("x");

function Const(value) {
    return {
        value: value,
        evaluate() {
            return this.value;
        },
        toString() {
            return String(this.value);
        },
        prefix(){
            return String(this.value);
        }
    }
}

const VARS = {
    "x": 0, "y": 1, "z": 2
}
const parse = (expression) => {
    const res = expression.split(/\s+/).filter(s => !!s);
    const stck = [];
    //:NOTE: foreach
    for (let i = 0; i < res.length; ++i) {
        if (res[i] in SUPPORTEDOPER) {
            const exp = SUPPORTEDOPER[res[i]];
            const args = stck.splice(stck.length - exp[1]);
            stck.push(new exp[0](...args));
        } else if (res[i] in VARS) {
            stck.push(new Variable(res[i]));
        } else {
            stck.push(new Const(+res[i]));
        }
    }
    return stck.pop();
}

function Exception(message) {
    this.message = message;
}
Exception.prototype = Error.prototype;
Exception.prototype.name = "Exception";
function CreateException(name) {
    this.name = name;
}
CreateException.prototype = Exception.prototype;

function exceptionFct(name, func) {
    function NewException() {
        Exception.call(this, func.apply(null, arguments));
    }
    NewException.prototype = new Exception(name);
    return NewException;
}

const MissingParentheses = exceptionFct("MissingParenthesesException", function(ind, tkn){
        return (ind + ": expected parentheses, but " + tkn);
    }
);
const ExtraClosingParentheses = exceptionFct("ExtraClosingParenthesesExcpetion", function(ind) {
        return (ind + ": extra parentheses found");
    }
);
const UnexpectedOperandException = exceptionFct("UnexpectedOperandException", function (exp, ind) {
        return (ind + ": expected valid variable, take " + exp);
    }
);
const UnexpectedOperationException = exceptionFct("UnexpectedOperationException", function (exp, ind) {
        return (ind + ": expected valid operation, take " + exp);
    }
);
const EmptyInputException = exceptionFct("EmptyInputException", function () {
        return ("expected non-empty input for parse %(");
    }
);

let ind = 0;
let balance = 0;

const skipWhitespaces = (exp) => {
    while (ind < exp.length && /\s+/.test(exp[ind])) {
        ind++;
    }
    return ind;
}
const getIdenty = (exp) => {
    skipWhitespaces(exp);
    let identy = "";
    while (ind < exp.length && exp[ind] !== '(' && !(/\s+/.test(exp[ind]))) {
        identy += exp[ind];
        ind++;
    }
    return identy;
}
const checkIsEmpty = (exp) => {
    if (exp.trim().length === 0)
        throw new EmptyInputException();
}

const parseConst = (exp) => {
    let num = "";
    if (exp[ind] === "-") {
        num += "-";
        ind++;
    }
    while (ind < exp.length && /^[\d.,:]+$/.test(exp[ind])) {
        num += exp[ind];
        ind++;
    }
    return parseInt(num);
}
const parseOperation = (exp, expParentheses) => {
    const token = getIdenty(exp);
    skipWhitespaces(exp);
    if (ind >= exp.length) {
        if (!expParentheses && !isNaN(token)) {
            return new Const(parseInt(token));
        } else if (!expParentheses && token in VARS) {
            return new Variable(token);
        } else {
            throw new UnexpectedOperandException(token, ind);
        }
    }
    if (!(token in SUPPORTEDOPER))
        throw new UnexpectedOperationException(token, ind);
    const operands = [];
    if (SUPPORTEDOPER[token][1] === 0) {
        let pOp = parseOperand(exp, true);
        while (pOp !== false) {
            operands.push(pOp);
            pOp = parseOperand(exp, true);
        }
    } else {
        for (let i = 0; i < SUPPORTEDOPER[token][1]; i++) {
            operands.push(parseOperand(exp));
        }
    }
    skipWhitespaces(exp);
    if (expParentheses && (ind >= exp.length || exp[ind] !== ")")) {
        throw new MissingParentheses(ind, "take " + exp[ind]);
    }
    if (expParentheses) {
        balance--;
        if (balance < 0) {
            throw new ExtraClosingParentheses(ind);
        }
        ind++;
    }
    return new (SUPPORTEDOPER[token][0])(...operands);
}
const parseOperand = (exp, parsingOp) => {
    skipWhitespaces(exp);
    if (exp[ind] === "(") {
        ind++;
        balance++;
        return parseOperation(exp, true);
    } else if (/^[\d.,:]+$/.test(exp[ind]) || exp[ind] === "-") {
        return new Const(parseConst(exp));
    } else if (exp[ind] in VARS) {
        ind++;
        return new Variable(exp[ind - 1]);
    } else {
        if (parsingOp)
            return false;
        throw new UnexpectedOperandException(exp[ind], ind);
    }
}

const parsePrefix = (exp) => {
    //console.log(exp)
    checkIsEmpty(exp);
    ind = 0;
    balance = 0;
    skipWhitespaces(exp);
    let expectParentheses = false;
    if (exp[ind] === "(") {
        balance++;
        expectParentheses = true;
        ind++;
    }
    const ans = parseOperation(exp, expectParentheses);
    skipWhitespaces(exp);
    if (ind < exp.length && exp[ind] === ")") {
        balance--;
    } else if (ind < exp.length) {
        throw new UnexpectedOperandException(ind);
    }
    if (balance !== 0 ) {
        throw new ExtraClosingParentheses(ind);
    }
    return ans;
}

//console.log(parsePrefix("(avg y(sum )y z)"))
