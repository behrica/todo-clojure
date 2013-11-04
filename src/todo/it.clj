(ns todo.it
  (:import org.openqa.selenium.server.SeleniumServer)
  (:use [clojure.java.shell :only [sh]])
  (:use ring.adapter.jetty)
  (:use todo.web)
  )


(defn -main [& [port]]

  (System/setProperty "webdriver.chrome.driver" "/home/carsten/bin/chromedriver")
  (println "start selenium")
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
