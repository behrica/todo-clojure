(ns todo.it
  (:require [clojure.test :refer :all ]
              [korma.db :refer :all ]
              [korma.core :refer :all ]
              [todo.core :refer :all ]
              [clj-time.core :only [date-time]]
              [clj-time.coerce :only [to-long]]
              [clojure.java.shell :refer [sh]]
              [ring.adapter.jetty :refer :all]
              [todo.web :refer :all]
              [todo.db :refer :all]
  )
  (:import org.openqa.selenium.server.SeleniumServer)
  )


(defn -main [& [port]]
  (init-db)
  (System/setProperty "webdriver.chrome.driver" "/home/carsten/bin/chromedriver")
  (let [selenium-server (org.openqa.selenium.server.SeleniumServer.)]
    (.start selenium-server)
    (let [appServer (run-jetty #'app {:port 8080 :join? false :daemon? true})]
      (println (:out (sh "protractor" "resources/protractor_conf.js")))
      (.stop appServer)
      (.stop selenium-server)
      (System/exit 0)
      )
    )
)
