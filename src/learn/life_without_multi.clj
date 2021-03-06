(require '[clojure.string :as str])

(defn my-print-vector [ob]
  (.write *out* "[")
  (.write *out* (str/join " " ob))
  (.write *out* "]"))

(defn my-println [ob]
  (my-print ob)
  (.write *out* "\n"))

(defmulti my-print class)

(defmethod my-print String [s]
  (.write *out* s))

(defmethod my-print nil [s]
  (.write *out* "nil"))

(defmethod my-print Number [n]
  (.write *out* (.toString n)))

(defmethod my-print :default [s]
  (.write *out* "#<")
  (.write *out* (.toString s))
  (.write *out* ">"))
