(declare m f)

(defn m [n]
  (if (zero? n)
    0
    (- n (f (m (dec n))))))

(defn f [n]
  (if (zero? n)
    1
    (- n (m (f (dec n))))))

;遅すぎてやばい。
;(f 250)


(def m (memoize m))
(def f (memoize f))

(m 250)

(f 250)

(def m-seq (map m (iterate inc 0)))
(def f-seq (map f (iterate inc 0)))

;(nth m-seq 100000)
;(nth f-seq 100000)

(nth m-seq 100)

