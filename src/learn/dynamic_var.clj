;; 動的なvar
;; fooの束縛は、全てのスレッドで共有される。
(def ^:dynamic  *foo* 10)
(def bar 10)

;; 別スレッドからも見れる。
(.start (Thread. (fn [] (println *foo*))))
(.start (Thread. (fn [] (println bar))))

;; bindingを使うと、スレッドローカルな束縛を作成できる。
(binding [*foo* 42] *foo*)
;; bindingの外側では値は変更されていない。
*foo*

;; 3行目で束縛した*foo*を参照する関数を作ってみる。
(defn print-foo [] (println *foo*) *foo*)
;; letで束縛しても、print-fooが参照する*foo*に束縛される値は変わらない。
(let [*foo* "let foo"] (print-foo))
;; bindingだと、print-fooが参照する*foo*に束縛される値は、bindされた値になる。
(binding [*foo* "let foo"] (print-foo))



;; おっそい関数
(defn ^:dynamic slow-double [n]
  (Thread/sleep 100)
  (* n 2))
(defn calls-slow-double []
  (map slow-double [1 2 1 2 1 2 1 2]))
;; かなり遅いよ。
(time (dorun (calls-slow-double)))

;; メモ化する。
(defn demo-memoize []
  ;; メモ化した関数をslow-doubleにbindし直す。
  (binding [slow-double (memoize slow-double)]
    ;; calls-slow-doubleでは、bindされているメモ化された関数を参照することになる。キm ry)
    (calls-slow-double)))
;; 2回しか計算されないから、それなりに早い。
(time (dorun (demo-memoize)))
