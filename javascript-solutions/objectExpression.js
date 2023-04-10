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

const SUPPORTEDOPER = {};
const setUp = (f, sign, operation) => {
    const exp = Object.create(ExpPrototype);
    exp.sign = sign;
    exp.operation = operation;
    const fnc = (...expressions) => {
        exp.expressions = expressions;
        return exp;
    }
    SUPPORTEDOPER[sign] = [f, operation.length];
    return fnc;
}
function createOperation(sign, operation) {
    const fnc = function (...expressions) {
        return setUp(fnc, sign, operation)(...expressions);
    }
    return fnc;
}

const Subtract = createOperation("-", (x, y) => x - y);
const Add = createOperation("+", (x, y) => x + y);
const Multiply = createOperation("*", (x, y) => x * y);
const Divide = createOperation("/", (x, y) => x / y);
const Negate = createOperation("negate", (x) => -x);
const Exp = createOperation("exp", Math.exp);
const Ln = createOperation("ln", Math.log);

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
    //:NOTE: foreach
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