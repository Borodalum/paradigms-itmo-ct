"use strict"

const ExpPrototype = {
    sign: "",
    operation: () => {},
    expressions: [],
    evaluate(...args) {
        return this.operation.apply(null,
            this.expressions.map((expr) => expr.evaluate.apply(expr, args))
        );
    },
    toString() {
        return this.expressions.map((expr) => expr.toString()).join(" ") + " " + this.sign;
    }
}
function setUp(sign, operation) {
    const exp = Object.create(ExpPrototype);
    exp.sign = sign;
    exp.operation = operation;
    return (...expressions) => {
        exp.expressions = expressions;
        return exp;
    }
}

const SUPPORTEDOPER = {}

function Subtract(...expressions) {
    SUPPORTEDOPER["-"] = [Subtract, 2];
    const func = setUp( "-", (x, y) => x - y);
    return func.apply(null, expressions);
}
function Add(...expressions) {
    SUPPORTEDOPER["+"] = [Add, 2];
    const func = setUp( "+", (x, y) => x + y);
    return func.apply(null, expressions);
}
function Multiply(...expressions) {
    SUPPORTEDOPER["*"] = [Multiply, 2];
    const func = setUp( "*", (x, y) => x * y);
    return func.apply(null, expressions);
}
function Divide(...expressions) {
    SUPPORTEDOPER["/"] = [Divide, 2];
    const func = setUp( "/", (x, y) => x / y);
    return func.apply(null, expressions);
}
function Negate(...expressions) {
    SUPPORTEDOPER["negate"] = [Negate, 1];
    const func = setUp( "negate", (x) => -x);
    return func.apply(null, expressions);
}

function Exp(...expressions) {
    SUPPORTEDOPER["exp"] = [Exp, 1];
    const func = setUp( "exp", Math.exp);
    return func.apply(null, expressions);
}
function Ln(...expressions) {
    SUPPORTEDOPER["ln"] = [Ln, 1];
    const func = setUp( "ln", Math.log);
    return func.apply(null, expressions);
}

function Variable(vrb) {
    return {
        vrb: vrb,
        evaluate(...args) {
            return args[VARS[vrb]];
        },
        toString() {
            return this.vrb;
        }
    }
}

function Const(value) {
    return {
        value: value,
        evaluate() {
            return this.value;
        },
        toString() {
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
    for (let i = 0; i < res.length; ++i) {
        if (res[i] in SUPPORTEDOPER) {
            const exp = SUPPORTEDOPER[res[i]];
            const args = stck.slice(stck.length - exp[1]);
            stck.length = stck.length - exp[1];
            stck.push(exp[0].apply(null, args));
        } else if (res[i] in VARS) {
            stck.push(new Variable(res[i]));
        } else {
            stck.push(new Const(parseInt(res[i])));
        }
    }
    return stck.pop();
}

let ex1 = parse("x 2 -");
let ex = new Subtract(new Variable("x"), new Const(3));
console.log(ex.toString())