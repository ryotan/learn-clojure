;; atomを使って、値を更新可能にする。
(def current-track (atom "Venus, the Bringer of Peace"))

@current-track

;; reset!で、atomの参照値を変更する。
(reset! current-track "Credo")

;; atomで複数の値を一度に更新するために、mapに入れてみる。
(def current-track (atom {:title "Credo" :composer "Byrd"}))
(reset! current-track {:title "Spem in Alium" :composer "Tallis"})
@current-track

;; swap!で、atomの値の一部を変更する。
(swap! current-track assoc :title "Sancte Deus")
;; swap!による値の変更は複数回試行される可能性がある。（STMのロジック上かな。）
;; なので、副作用を起こしてはいけない。

