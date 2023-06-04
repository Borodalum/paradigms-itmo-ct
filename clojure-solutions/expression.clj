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
(def toStringPostfix (method :toStringPostfix))
(def evaluate (method :evaluate))
(defn expressionProto [evaluate toString toStringPostfix]
      {
       :evaluate evaluate
       :toString toString
       :toStringPostfix toStringPostfix
       })

(def _getValue (field :value))
(def toStrValues (fn [this] (str (_getValue this))))
(def OneValueExpressions (constructor
                           (fn [this evaluate] (assoc this :evaluate evaluate))
                           (expressionProto
                             (fn [this _] 0)
                             (fn [this] (str (_getValue this)))
                             (fn [this] (str (_getValue this)))
                             )
                           ))
(defn onevalueFactory [evaluate] (constructor
                                   (fn [this value] (assoc this :value value))
                                   (OneValueExpressions evaluate)
                                   ))
(def Constant (onevalueFactory (fn [this _] (_getValue this))))
(def Variable (onevalueFactory (fn [this args] (get args (str (Character/toLowerCase (first (_getValue this))))))))

(def _getOp (field :operation))
(def _getOperands (field :operands))
(def _getOpStr (field :operationString))
(def AbstractExpr (constructor
                    (fn [this operation opstr] (assoc this :operation operation :operationString opstr))
                    (expressionProto
                      (fn [this args] (apply (_getOp this) (map #(evaluate % args) (_getOperands this))))
                      (fn [this] (str "(" (_getOpStr this) " " (clojure.string/join " " (mapv toString (_getOperands this))) ")"))
                      (fn [this] (str "(" (clojure.string/join " " (mapv toStringPostfix (_getOperands this))) " " (_getOpStr this) ")"))
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
(def Inc (expFactory #(+ % 1) "++"))
(def Dec (expFactory #(- % 1) "--"))

(def suppObject {'++ Inc '-- Dec '+ Add '- Subtract '* Multiply '/ Divide 'negate Negate 'sin Sin 'cos Cos})
(defn parseObject [string] ((parserFact Constant Variable suppObject) (read-string string)))

(load-file "parser.clj")

(def *space (+char " \t\n\r"))
(def *ws (+ignore (+star *space)))
(def *digit (+char "0123456789"))
(def *number (+map read-string (+str (+seq (+opt (+char "-")) (+str (+plus *digit)) (+char ".") (+plus *digit)))))

(def *all-chars (mapv char (range 32 128)))
(def *letter (+char (apply str (filter #(Character/isLetter %) *all-chars))))

(def *identifierVar (+str (+seq *ws (+str (+plus (+char "xyzXYZ"))))))

(def *operChars (+or *letter (+char "+-*/") (+plus (+char "+-"))))
(def *operation (+str (+seq *ws (+str (+plus *operChars)))))

(defn *expression [] (delay
                       (+or
                         (+map Constant *number)
                         (+map Variable (+seqf str *identifierVar))
                         (+map #(suppObject %) (+seqf symbol *operation))
                         (+map #(apply (last %) (drop-last %))
                               (+seqn 1 *ws (+char "(") (+seqf cons *ws (*expression) (+star (+seqn 0 *ws (*expression)))) *ws (+char ")")))
                         )))

(def parseObjectPostfix (+parser (+seqn 0 *ws (*expression) *ws)))
