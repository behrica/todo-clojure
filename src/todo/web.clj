(ns todo.web
  (:require [compojure.core :refer :all]
            [ring.middleware.json-params :refer :all]
            [todo.core :refer :all]
            [ring.adapter.jetty :refer :all]
            [clj-json.core :as json]
            [todo.db :refer :all ]
            [compojure.route :as route]
            [clj-time.core :refer [date-time today]
             ]))

(defn make-todo-for-json [{title :TITLE date :DATE}]
  {:title title :date (str date)}
)

(defn transform-todos [todo-list]
  (map make-todo-for-json todo-list)
)

(defn json-response [data & [status]]
  {:status (or status 200)
   :headers {"Content-Type" "application/json"}
   :body (json/generate-string data)})

(defroutes handler
  (GET "/rest/todos" []
    (json-response (transform-todos (todo.db/todos))))
  (POST "/rest/todos" [title]
    (json-response (todo.db/add-todo (new-todo title (today))) 201))
  (route/resources "/" )
  (route/not-found "404 Not Found")
)

(def app
  (-> handler
    wrap-json-params))
