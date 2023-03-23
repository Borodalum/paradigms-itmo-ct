"use strict"

let baseExp = (oper) => (...args) => {
    return (...args2) => {
        let evl = [];
        for (let i = 0; i < args.length; i++) {
            evl.push(args[i].apply(null, args2));
        }
        return oper.apply(null, evl);
    }
}

let subtract = baseExp((x, y) => x - y);
let multiply = baseExp((x, y) => x * y);
let add = baseExp((x, y) => x + y);
let divide = baseExp((x, y) => x / y);
let negate = baseExp((x) => -x);

let cnst = (value) => () => value;
let one = cnst(1);
let two = cnst(2);
const CONSTANTS = {
    "one": one, "two": two
}

const VARIABLES = {
    "x": 0, "y": 1, "z": 2
}
let variable = (vrb) => {
    return (...args) => args[VARIABLES[vrb]];
}

let parse = (expression) => {
    let resource = expression.split(/\s+/).filter(i => !!i);
    let OPERATIONS = {
        "+": add, "-": subtract, "*": multiply, "/": divide, "negate": negate
    };
    let ARGS = {
        "+": 2, "-": 2, "*": 2, "/": 2, "negate": 1
    };
    let stck = [];
    for (let i = 0; i < resource.length; i++) {
        if (resource[i] in VARIABLES) {
            stck.push(variable(resource[i]));
            continue;
        }
        if (resource[i] in CONSTANTS) {
            stck.push(CONSTANTS[resource[i]]);
            continue;
        }
        if (resource[i] in OPERATIONS) {
            let args = [];
            for (let j = 0; j < ARGS[resource[i]]; j++) {
                args.push(stck.pop());
            }
            args.reverse();
            stck.push(OPERATIONS[resource[i]].apply(null, args));
            continue;
        }
        stck.push(cnst(parseInt(resource[i])));
    }
    return stck.pop();
}

/*let expr = subtract(
    multiply(
        cnst(2),
        variable("x")
    ),
    cnst(3)
);

console.log(expr(5, 0, 0));*/