(ns todo.web-test
  (:require [clojure.test :refer :all ]
              [todo.core :refer :all]
              [todo.web :refer :all]
              [ring.adapter.jetty :refer :all :as jetty]
              [clj-http.lite.client :as client]
              [clj-time.core :refer :all]
              )
    (:use [midje.sweet :refer :all ])
  (:import [org.mortbay.jetty.Server])
)

(defonce server (jetty/run-jetty #'todo.web/app {:port 8080 :join? false}))

(defn startServer []
  (.start server)
)
(defn stopServer []
  (.stop server)
)



(with-state-changes [(before :facts (startServer))
                     (after :facts (stopServer))]
  (fact "should give answer on 'todos'"
    (:status (client/get "http://localhost:8080/rest/todos")) => 200)
  (fact "should give answer on 'index'"
    (:status (client/get "http://localhost:8080/index.html")) => 200)
  (fact "should give answer on 'todo'"
    (:status (client/post "http://localhost:8080/rest/todo")) => 200)

  (fact "should call add-todo when /rest/todo is requested"
    (:body (todo.web/handler {:request-method :post :uri "/rest/todo" :params {:title "t123"}})) => "\"\""
    (provided (todo.db/add-todo {:title "t123", :date (date-time 2012 01 01)}) => nil)
  )

  )


(fact "should transform date-time to string"
  (todo.web/transform-todos [{:TITLE "atitle" :DATE (clj-time.core/date-time 2000 01 01)}]) => [{:date "2000-01-01T00:00:00.000Z", :title "atitle"}]
)