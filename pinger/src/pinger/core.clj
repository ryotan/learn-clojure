(ns pinger.core
  (:import (java.net URL HttpURLConnection))
  (:require [pinger.scheduler :as scheduler]
            [clojure.tools.logging :as logger]
            [pinger.config :as config])
  (:gen-class))

(defn response-code [address]
  (let [conn ^HttpURLConnection (.openConnection (URL. address))
        code (.getResponseCode conn)]
    (when (< code 400)
      (-> conn .getInputStream .close))
    code))

(defn available? [address]
  (= 200 (response-code address)))

(defn record-availability [address]
  (if (available? address)
    (do
      (logger/info (str address " is responding normally")))
    (do
      (logger/error (str address " is not available")))))

(defn check []
  (let [addresses (config/urls (config/config))]
    (doseq [address addresses]
      (record-availability address))))

(def immediately 0)
(def every-minute (* 60 1000))

(defn start [e]
  "REPL helper. Start pinger on executor e."
  (scheduler/periodically e check
                          :initial-delay immediately
                          :delay every-minute))

(defn stop [e]
  "REPL helper. Stop executor e."
  (scheduler/shutdown-executor e))

(defn -main []
  (start (scheduler/scheduled-executor 1)))
