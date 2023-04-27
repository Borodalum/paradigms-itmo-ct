(defn expression [operation]
      (fn [& args] #(apply operation ((apply juxt args) %)))
      )
(defn goodDivide [& args] (apply #(/ %1 (double %2)) args))

(defn variable [var] #(% var))
(defn constant [value] (fn [args] value))

(def add (expression +))
(def subtract (expression -))
(def multiply (expression *))
(def divide (expression goodDivide))
(def negate (expression #(- %)))
(def exp (expression #(Math/exp %)))
(def ln (expression #(Math/log %)))

(def suppOperations {'+ add '- subtract '* multiply '/ divide 'negate negate 'exp exp 'ln ln})

(defn helpParse [expression]
      (if (list? expression)
        (apply (suppOperations (first expression)) (map helpParse (rest expression)))
        (if (number? expression)
          (constant expression)
          (variable (str expression))
          )
        )
      )
(defn parseFunction [string] (helpParse (read-string string)))