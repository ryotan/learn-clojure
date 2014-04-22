(defn tail-fibo
  "末尾再帰でフィボナッチ数列を計算する。"
  [n]
  (letfn [(fib [current next n]
               (if (zero? n)
                 current
                 (fib next (+ current next) (dec n))))]
    (fib 0N 1N n)))

(tail-fibo 10)
;(tail-fibo 1000000)

(defn recur-fibo
  "末尾再帰（Clojureが最適化できるように、recurを使用）でフィボナッチ数列を計算する。"
  [n]
  (letfn [(fib [current next n]
               (if (zero? n)
                 current
                 (recur next (+ current next) (dec n))))]
    (fib 0N 1N n)))

(recur-fibo 10)
;(recur-fibo 1000000)

(defn lazy-seq-fibo
  "lazy-seqでフィボナッチ数列を計算する。"
  ([]
   ;; 基底となるseqの定義
   (concat [0 1] (lazy-seq-fibo 0N 1N)))
  ([a b]
   (let [n (+ a b)]
     ;; 必要な場合のみ計算するように、lazy-seqにする
     (lazy-seq
      ;; フィボナッチ数列を帰納的に計算
      (cons n (lazy-seq-fibo b n))))))

(take 10 (lazy-seq-fibo))

;(rem (nth (lazy-seq-fibo) 1000000) 1000)

(defn fibo
  "シーケンスライブラリでフィボナッチ数列を計算する。"
  []
  (map first (iterate (fn [[a b]] [b (+ a b)]) [0N 1N])))

(take 10 (fibo))

(nth (fibo) 100000)
