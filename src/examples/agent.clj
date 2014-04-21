;; 初期値0のagentを作る。
(def counter (agent 0))

;; sendを使用して、agentに値の更新を依頼する。
(send counter inc)

;; await で、agentによる更新が完了するのを待機する。（タイムアウトせず、永遠にブロックし続ける。）
(await counter)
(send counter inc)

;; await-for で、agentによる更新が完了するのを待機する。（タイムアウトした場合のみ、nilが返却される。）
(await-for 100 counter)
(send counter inc)

;; @ か deref で参照する。
@counter
(deref counter)


;; validator を設定する。
(def counter (agent 0 :validator number?))
(send counter inc)

;; validation errorとなる値を設定してみる。
(send counter (fn [_] "aa"))
;; validation error発生後に、値の更新を依頼すると、例外が発生する。
(send counter inc)
;; validatiorで発生したエラーは、agent-errorsで取得できる。
(agent-errors counter)
;; clear-agent-errors で、agentの値をvalidation error発生前に戻して、再度利用できるようにする。
(clear-agent-errors counter)
;; clearすれば、使える。
(send counter inc)
@counter



;; トランザクション中で agent 使ってみるサンプル。
(def messages (ref ()))
(def backup-agent (agent "output/messages-backup.clj"))
(defn add-message-with-backup [msg]
  ;; messageの更新に成功した時だけ、backupする。
  (dosync
   ;; messagesの更新後の値をsnapshotにbind
   (let [snapshot (commute messages conj msg)]
     ;; アクション関数が処理をブロックするような場合は、send-offを使う。
     ;; sendを使ってしまうと、他のagentの動作が止まってしまうかもしれない。
     (send-off backup-agent (fn [filename] (spit filename snapshot) filename))
     snapshot)))
(add-message-with-backup {:sender "John" :text "Message One"})
(add-message-with-backup {:sender "John" :text "Message Two"})
