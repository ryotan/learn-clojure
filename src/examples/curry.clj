
(defn faux-curry
  "カリー化されてるっぽい感じに動く関数を生成する。
   ただし、すべての引数が固定されても結果を返さないから、厳密にはカリー化関数じゃない。"
  [& args]
  (apply partial partial args))


;; 部分適用
(def add-3 (partial + 3))

(add-3 7)

(def add-4 ((faux-curry +) 4))

(add-4 7)

((faux-curry true?) (= 1 1))

(((faux-curry true?) (= 1 1)))

