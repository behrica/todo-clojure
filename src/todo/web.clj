(ns todo.web
  (:use compojure.core)
  (:use ring.middleware.json-params)
  (:use todo.core)
  (:use ring.adapter.jetty)
  (:use clj-time.core )
  (:require [clj-json.core :as json])
  (:require [todo.db :refer :all ])
  (:require [compojure.route :as route])

)

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
