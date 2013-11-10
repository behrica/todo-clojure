(ns todo.web-test
  (:require [clojure.test :refer :all ]
              [todo.core :refer :all]
              [todo.web :refer :all]
              [todo.db :refer :all]
              [ring.adapter.jetty :refer :all :as jetty]
              [clj-http.client :as client]
              [clj-time.core :refer [date-time]]
              [midje.sweet :refer :all ]
              [clj-json.core :refer :all]
              [korma.core :refer :all]
              )
  (:import [org.mortbay.jetty.Server]))

(defonce server (jetty/run-jetty #'todo.web/app {:port 8080 :join? false}))

(defn startServer []
  (.start server)
)
(defn stopServer []
  (.stop server)
)


(with-state-changes [(before :facts (do (delete TODOS)(startServer)))
                     (after :facts (stopServer))]
  (fact "should give answer on 'todos'"
    (:status (client/get "http://localhost:8080/rest/todos")) => 200)
  (fact "should give answer on 'index'"
    (:status (client/get "http://localhost:8080/index.html")) => 200)
  (fact "should call add-todo when /rest/todo is requested"
    (:body (todo.web/handler {:request-method :post :uri "/rest/todo" :params {:title "t123" :uuid "xyz"}})) => "\"\""
    (provided (todo.db/add-todo {:uuid "xyz" :title "t123", :date (date-time 2012 01 01)}) => nil))

  (fact "should give answer on 'todo'"
    (:status (client/post "http://localhost:8080/rest/todo" {:content-type :json  :form-params {:uuid "xyz1"} })) => 201)

  (fact "should transform date-time to string"
     (todo.web/transform-todos [{:uuid nil :TITLE "atitle" :DATE (clj-time.core/date-time 2000 01 01)}])
        => [{:uuid nil :date "2000-01-01T00:00:00.000Z", :title "atitle"}])
)

(defn init-with-one-todo []
  (add-todo (struct todo "xyz1" "newTitle" (clj-time.core/date-time 2020 01 01)))
)

(with-state-changes [(before :facts (do
                                      (delete TODOS)
                                      (init-with-one-todo)
                                      (startServer)))
                     (after :facts (stopServer))]
  (fact "post a todo should be idempotent"
    (:status (client/post "http://localhost:8080/rest/todo" {:content-type :json  :form-params {:uuid "xyz1"} })) => 201)
)