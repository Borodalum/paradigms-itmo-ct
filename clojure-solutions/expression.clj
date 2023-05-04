(defn expression [operation]
      (fn [& args] #(apply operation ((apply juxt args) %)))
      )
(defn goodDivide [& args] (apply #(/ %1 (double %2)) args))
(defn negateImpl [arg] (- arg))

(defn variable [var] #(% var))
(def constant constantly)

(def add (expression +))
(def subtract (expression -))
(def multiply (expression *))
(def divide (expression goodDivide))
(def negate (expression #(- %)))
(def exp (expression #(Math/exp %)))
(def ln (expression #(Math/log %)))

(def suppOperations {'+ add '- subtract '* multiply '/ divide 'negate negate 'exp exp 'ln ln})

(defn parserFact [cnst vrb howToTakeOper]
      (fn helpParse [expression]
          (if (list? expression)
            (apply (howToTakeOper (first expression)) (map helpParse (rest expression)))
            (if (number? expression)
              (cnst expression)
              (vrb (str expression))
              )
            )
          )
      )
(defn parseFunction [string] ((parserFact constant variable suppOperations) (read-string string)))

(load-file "proto.clj")

(def toString (method :toString))
(def evaluate (method :evaluate))
(defn expressionProto [evaluate toString]
      {
       :evaluate evaluate
       :toString toString
       })

(def _getValue (field :value))
(def Constant (constructor
                (fn [this value] (assoc this :value value))
                (expressionProto
                  (fn [this map] (_getValue this))
                  (fn [this] (str (_getValue this)))
                  )
                ))
(def Variable (constructor
                (fn [this vrb] (assoc this :value vrb))
                (expressionProto
                  (fn [this args] (args (_getValue this)))
                  (fn [this] (_getValue this))
                  )
                ))

(def _getOp (field :operation))
(def _getOperands (field :operands))
(def _getOpStr (field :operationString))
(def AbstractExpr (constructor
                    (fn [this operation opstr] (assoc this :operation operation :operationString opstr))
                    (expressionProto
                      (fn [this args] (apply (_getOp this) (map #(evaluate % args) (_getOperands this))))
                      (fn [this] (str "(" (_getOpStr this) " " (clojure.string/join " " (mapv toString (_getOperands this))) ")"))
                      )
                    ))
(defn expFactory [operation opStr] (constructor
                  (fn [this & operands] (assoc this :operands operands))
                  (AbstractExpr operation opStr)
                  ))
(def Subtract (expFactory - "-"))
(def Add (expFactory + "+"))
(def Multiply (expFactory * "*"))
(def Divide (expFactory goodDivide "/"))
(def Negate (expFactory negateImpl "negate"))
(def Sin (expFactory #(Math/sin %) "sin"))
(def Cos (expFactory #(Math/cos %) "cos"))

(def suppObject {'+ Add '- Subtract '* Multiply '/ Divide 'negate Negate 'sin Sin 'cos Cos})
(defn parseObject [string] ((parserFact Constant Variable suppObject) (read-string string)))