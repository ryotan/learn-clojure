;; refを使って、変更可能な参照を作る。
(def current-track (ref "Mars, the Bringer of War"))

;; derefか、@リーダーマクロを使って参照先の値を取得する。
(deref current-track)
@current-track

;; ref-setで、参照先を変更する。
;; ただし、トランザクション内でないと、変更できない。
(dosync
  (ref-set current-track (ref "Venus, the Bringer of Peace"))
)
@current-track

;; 複数のref-setがまとめて更新される。（どちらかだけが更新されている状態、は公開されない。）
(def current-track (ref "Venus, the Bringer of Peace"))
(def current-composer (ref "Holst"))
(dosync
 (ref-set current-track "Credo")
 (ref-set current-composer "Byrd"))




(defrecord Message [sender text])
(Message. "Aaron" "Hello")

(def messages (ref ()))

;; dosyncを使って、メッセージのリストを更新する。（あまりうまくない。）
(defn naive-add-message [msg]
  (dosync (ref-set messages (cons msg @messages))))

;; alterを使って、メッセージのリストを更新する。（あまりうまくない。）
(defn add-message [msg]
  (dosync (alter messages conj msg))) ; conj の第1引数に@messagesが渡される。

(add-message (Message. "user 1" "hello"))
(add-message (Message. "user 2" "hello"))

