(declare my-odd? my-even?)

(defn my-odd? [n]
  (if (= n 0)
    false
    (my-even? (dec n))))

(defn my-even? [n]
  (if (= n 0)
    true
    (my-odd? (dec n))))

(map my-even? (range 10))

;(my-even? (* 1000 1000 1000))


(defn parity [n]
  (loop [n n par 0]
    (if (= n 0)
      par
      (recur (dec n) (- 1 par)))))

(map parity (range 10))


(defn trampoline-fibo [n]
  (let [fib (fn fib [f-2 f-1 current]
              (let [f (+ f-2 f-1)]
                (if (= n current)
                  f
                  #(fib f-1 f (inc current)))))]
    (cond
     (= n 0) 0
     (= n 1) 1
     :else (fib 0N 1 2)
     )))

(trampoline trampoline-fibo 9)

;(rem (trampoline trampoline-fibo 1000000) 1000)

(declare my-odd? my-even?)

(defn my-odd? [n]
  (if (= n 0)
    false
    #(my-even? (dec n))))

(defn my-even? [n]
  (if (= n 0)
    true
    #(my-odd? (dec n))))

(trampoline my-even? 1000000)



