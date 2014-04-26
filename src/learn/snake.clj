(ns learn.snake
  (:import (java.awt Color Dimension)
           (javax.swing JPanel JFrame Timer JOptionPane)
           (java.awt.event ActionListener KeyListener))
  (:use examples.import-static))
(import-static java.awt.event.KeyEvent VK_LEFT VK_RIGHT VK_UP VK_DOWN)

(def width
  "ゲームエリアの横幅"
  75)
(def height
  "ゲームエリアの縦幅"
  50)
(def point-size
  "1セルのピクセル数"
  10)
(def turn-millis
  "画面の更新間隔"
  75)
(def win-length
  "ゲーム勝利となるヘビの長さ"
  5)
(def directions
  "方向を表す定数"
  { VK_LEFT  [-1  0]
    VK_RIGHT [ 1  0]
    VK_UP    [ 0 -1]
    VK_DOWN  [ 0  1]})

(defn add-points
  "ゲームエリア上の位置に、移動量を加算して、移動後の位置を返却する。"
  [pt & mv]
  (vec (apply map + pt mv)))

(add-points [10 10] [-1 0] [-1 0] [0 1] [0 -1])

(defn point-to-screen-rect
  "座標を対応するセルに変換する。"
  [pt]
  (map #(* point-size %)
       ;; 指定された座標を起点として、右に1セル分、下に1セル分
       [(pt 0) (pt 1) 1 1]))

(point-to-screen-rect [5 10])

(defn create-apple
  "ランダムな位置にりんごを生成する。"
  []
  {:location [(rand-int width) (rand-int height)]
   :color (Color. 210 50 90)
   :type :apple})

(defn create-snake
  "ヘビを生成する。"
  []
  {:body (list [1 1])
   :direction [1 0]
   :type :snake
   :color (Color. 15 160 70)})

(defn move
  "ヘビを移動させる。りんごを食べて伸びるときには、growをtrueにする。"
  [{:keys [body direction] :as snake} & grow]
  (assoc snake :body
    (cons
     ;; 先頭をdirection方向に移動させる。
     (add-points (first body) direction)
     (if grow
       ;; 伸びるときは、今のやつをそのまま残す。
       body
       ;; 伸びない時は、ケツを落とす。
       (butlast body)))))

(defn win?
  "ヘビの長さが勝利条件に到達しているかどうか。"
  [{body :body}]
  (>= (count body) win-length))

(defn head-overlaps-body?
  "ヘビの先頭が胴体と交差していないかどうか。"
  [{[head & body] :body}]
  (contains? (set body) head))

(def lose? head-overlaps-body?)

(defn eats?
  "ヘビがりんごを食べられるかどうか"
  [{[snake-head] :body} {apple :location}]
  (= snake-head apple))

(defn turn
  "ヘビの方向を変える。"
  [snake new-direction]
  (assoc snake :direction new-direction))

(defn reset-game
  "ゲームを初期化する。"
  [snake apple]
  (dosync
   (ref-set apple (create-apple))
   (ref-set snake (create-snake)))
  nil)

(defn update-direction
  "ヘビの方向を変える。"
  [snake new-direction]
  (when new-direction
    (dosync
     (alter snake turn new-direction))))

(defn update-positions
  "ヘビの位置を更新する。"
  [snake apple]
  (dosync
   (if (eats? @snake @apple)
     (do
       (ref-set apple (create-apple))
       (alter snake move :grow))
     (do
       (alter snake move))))
  nil)

(defn fill-point [g pt color]
  (let [[x y width height] (point-to-screen-rect pt)]
    (.setColor g color)
    (.fillRect g x y width height)))

(defmulti paint (fn [g object & _] (:type object)))

(defmethod paint :apple [g {:keys [location color]}]
  (fill-point g location color))

(defmethod paint :snake [g {:keys [body color]}]
  (doseq [point body]
    (fill-point g point color)))

(defn game-panel [frame snake apple]
  (proxy [JPanel ActionListener KeyListener] []
    (paintComponent [g]
      (proxy-super paintComponent g)
      (paint g @snake)
      (paint g @apple))
    (actionPerformed [e]
      (update-positions snake apple)
      (when (lose? @snake)
        (reset-game snake apple)
        (JOptionPane/showMessageDialog frame "You lose!"))
      (when (win? @snake)
        (reset-game snake apple)
        (JOptionPane/showMessageDialog frame "You win!"))
      (.repaint this))
    (keyPressed [e]
      (update-direction snake (directions (.getKeyCode e))))
    (getPreferredSize []
      (Dimension. (* (inc width) point-size)
                  (* (inc height) point-size)))
    (keyReleased [e])
    (keyTyped [e])))

(defn game []
  (let [snake (ref (create-snake))
        apple (ref (create-apple))
        frame (JFrame. "Snake")
        panel (game-panel frame snake apple)
        timer (Timer. turn-millis panel)]
    (doto panel
      (.setFocusable true)
      (.addKeyListener panel))
    (doto frame
      (.add panel)
      (.pack)
      (.setVisible true))
    (.start timer)
    [snake, apple, timer]))


