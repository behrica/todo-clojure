(ns todo.web
  (:require [compojure.core :refer :all]
            [ring.middleware.json-params :refer :all]
            [todo.core :refer :all]
            [ring.adapter.jetty :refer :all]
            [clj-json.core :as json]
            [todo.db :refer :all ]
            [compojure.route :as route]
            [clj-time.core :refer [date-time]]
))

(defn transform-todos [todo-list]
  (map #(struct todo (:TITLE %) (str (:DATE %)) ) todo-list)
)

(defn json-response [data & [status]]
  {:status (or status 200)
   :headers {"Content-Type" "application/json"}
   :body (json/generate-string data)})

(defroutes handler
  (GET "/rest/todos" []
    (json-response (transform-todos (todo.db/todos))))
  (POST "/rest/todo" [title]
    (todo.db/add-todo (struct todo title (date-time 2012 01 01)))
    (json-response ""))

  (route/resources "/" )
  (route/not-found "404 Not Found")
)

(def app
  (-> handler
    wrap-json-params))
