(defmacro unless [expr form]
  (list 'if expr nil form))

(unless false (println "this should print"))
(unless true  (println "this should not print"))

(defmacro chain
  ([x form] `(. ~x ~form))
  ([x form & more] (concat `(chain (. ~x ~form) ~@more))))

(macroexpand '(chain arm getHand getFinger))

(defmacro bench [expr]
  `(let [start# (System/nanoTime)
         result# ~expr]
     {:result result# :elapsed (- (System/nanoTime) start#)}))

(bench (str "a" "b"))

(defmacro and
  ([] true)
  ([x] x)
  ([x & rest] `(let [and# ~x]
                 (if and# (and ~@rest) and#))))

(and 1 0 nil false)
(and 1 0 false nil)

(defmacro or
  ([] false)
  ([x] x)
  ([x & rest] `(let [or# ~x]
                 (if or# or# (or ~@rest)))))

(or nil false 1 0)
(or nil false true 1)

(defmacro with-out-str
  [& body]
  `(let [s# (java.io.StringWriter.)]
     (binding [*out* s#]
       ~@body
       (str s#))))

(with-out-str (println "this should not be shown."))

