(defproject pinger "0.1.0-SNAPSHOT"
  :description "A website availability tester"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure/clojure "1.6.0"]
                 [org.clojure/tools.logging "0.2.6"]
                 [log4j "1.2.16"]
                 [javax.mail/mail "1.4.7"]]
  :aot :all
  :main pinger.core)
