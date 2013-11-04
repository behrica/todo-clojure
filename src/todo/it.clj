(ns todo.it
  (:require [clojure.test :refer :all ]
              [korma.db :refer :all ]
              [korma.core :refer :all ]
              [todo.core :refer :all ]
              [clj-time.core :only [date-time]]
              [clj-time.coerce :only [to-long]]

              )
  (:import org.openqa.selenium.server.SeleniumServer)
  (:use [clojure.java.shell :only [sh]])
  (:use ring.adapter.jetty)
  (:use todo.web)
  (:use todo.db)
  )

(defn- init-db []
  (delete TODOS)
  (add-todo (struct todo "newTitle" (clj-time.core/date-time 2020 01 01)))
  (add-todo (struct todo "newTitle" (clj-time.core/date-time 2020 01 02)))
)

(defn -main [& [port]]
  (init-db)
  (System/setProperty "webdriver.chrome.driver" "/home/carsten/bin/chromedriver")
  (let [server (org.openqa.selenium.server.SeleniumServer.)]
    (.start server)
    (let [appServer (run-jetty #'app {:port 8080 :join? false :daemon? true})]
      (println (:out (sh "protractor" "resources/protractor_conf.js")))
      (.stop appServer)
      (.stop server)
      (System/exit 0)
      )
    )
)
