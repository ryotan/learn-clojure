; START:ns
(ns examples.exploring
  ; :import, :require, :useが使えるらしい。
  ; ちなみに、importはJavaクラスにしか使えないよ。
  (:require [clojure.string :as str])
  (:import (java.io File)))
; END:ns

; START:date
(defn date [person-1 person-2 & chaperones]
  (println person-1 "and" person-2
	   "went out with" (count chaperones) "chaperones."))
; END:date

; START:if
(defn is-small? [number]
  (if (< number 100) "yes"))
(is-small? 50)
; elseがないので、nilが返ってくる。
(is-small? 100)
; END:if
(def is-small-with-if? is-small?)

; START:if-else
(defn is-small? [number]
  (if (< number 100) "yes" "no"))
; elseの場合の式の値が返ってくる。
(is-small? 100)
; END:if-else
(def is-small-with-else? is-small?)

; START:do
(defn is-small? [number]
  (if (< number 100)
    "yes"
    ; doがあるということは、何らかの副作用が発生することが期待される。
    ; （最後のフォーム以外の返り値は無視されるから、最後以外のフォームが意味を持つには副作用を起こすしかない。）
    (do
      (println "Saw a big number" number)
      "no")))
; END:do
(def is-small-with-do? is-small?)
(is-small? 100)

(defn demo-loop []
; START: loop
  (loop [result [] x 5]
    (if (zero? x)
      result
      (recur (conj result x) (dec x))))
; END: loop
)

; START:countdown
(defn countdown [result x]
  (if (zero? x)
    result
    (recur (conj result x) (dec x))))
; END:countdown

; START: indexed
(defn indexed [coll] (map-indexed vector coll))
; END: indexed

; START: index-filter
(defn index-filter [pred coll]
  (when pred
    (for [[idx elt] (indexed coll) :when (pred elt)] idx)))
; END: index-filter
; START:index-of-any
(defn index-of-any [pred coll]
  (first (index-filter pred coll)))
; END: index-of-any

; START:greeting
(defn greeting
  "Returns a greeting of the form 'Hello, username.'"
  [username]
  (str "Hello, " username))
; END:greeting
(def simple-greeting greeting)

; START:greeting-with-default
(defn greeting
  "Returns a greeting of the form 'Hello, username.'
   Default username is 'world'."
  ([] (greeting "world"))
  ([username] (str "Hello, " username)))
; END:greeting-with-default
(def greeting-with-default greeting)

; START:indexable-word
(defn indexable-word? [word]
  (> (count word) 2))
; END:indexable-word

; START:indexable-words
(defn indexable-words [text]
  (let [indexable-word? (fn [w] (> (count w) 2))]
    (filter indexable-word? (str/split text #"\W+"))))
; END:indexable-words

; START:make-greeter
(defn make-greeter [greeting-prefix]
  (fn [username] (str greeting-prefix ", " username)))
; END:make-greeter

; START:square-corners
(defn square-corners [bottom left size]
  (let [top (+ bottom size)
	right (+ left size)]
    [[bottom left] [top left] [top right] [bottom right]]))
; END:square-corners

; START:busted
(defn ^{:test (fn []
                (assert (nil? (busted))))}
  busted [] "busted")
; END:busted

(def vinge {:first-name "Vernor" :last-name "Vinge"})

; START:greet-author-1
(defn greet-author-1 [author]
  (println "Hello," (:first-name author)))
; END:greet-author-1

; START:greet-author-2
(defn greet-author-2 [{fname :first-name}]
  (println "Hello," fname))
; END:greet-author-2

; START:ellipsize
(require '[clojure.string :as str])
(defn ellipsize [words]
  ; str/splitで分割された文字列の最初の3つが、それぞれw1,w2,w3に分配束縛される。(Destructuring)
  (let [[w1 w2 w3] (str/split words #"\s+")]
    (str/join " " [w1 w2 w3 "..."])))

(ellipsize "The quick brown fox jumps over the lazy dog.")
; END:ellipsize


(defn indexed [coll] (map-indexed vector coll))

(indexed "abcde")

(defn index-filter [pred coll]
  (when pred
    (for [[idx elt] (indexed coll) :when (pred elt)] idx)))

(index-filter #{\a \b} "abcde")

(defn index-of-any [pred coll]
  (first (index-filter pred coll)))

(index-of-any #{\z \a} "z/os")
