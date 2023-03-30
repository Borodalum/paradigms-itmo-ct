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
function setUp(expressions, sign, operation) {
    const exp = Object.create(ExpPrototype);
    exp.expressions = expressions;
    exp.sign = sign;
    exp.operation = operation;
    return exp;
}

function Subtract(...expressions) {
    return setUp(expressions, "-", (x, y) => x - y);
}
function Add(...expressions) {
    return setUp(expressions, "+", (x, y) => x + y);
}
function Multiply(...expressions) {
    return setUp(expressions, "*", (x, y) => x * y);
}
function Divide(...expressions) {
    return setUp(expressions, "/", (x, y) => x / y);
}
function Negate(...expressions) {
    return setUp(expressions, "negate", (x) => -x);
}

function Exp(...expressions) {
    return setUp(expressions, "exp", (x) => Math.exp(x))
}
function Ln(...expressions) {
    return setUp(expressions, "ln", (x) => Math.log(x))
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

const SUPPORTEDOPER = {
    "+": [Add, 2],
    "-": [Subtract, 2],
    "*": [Multiply, 2],
    "/": [Divide, 2],
    "negate": [Negate, 1],
    "exp": [Exp, 1],
    "ln": [Ln, 1]
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