(ns todo.web
  (:use compojure.core)
  (:use ring.middleware.json-params)
  (:use todo.core)

  (:require [clj-json.core :as json])
  (:require [todo.db :refer :all ])
)

(defn transform-todos [todo-list]
  (map #(struct todo (:TITLE %) (str (:DATE %)) ) (todo-list))
)

(defn json-response [data & [status]]
  {:status (or status 200)
   :headers {"Content-Type" "application/json"}
   :body (json/generate-string data)})

(defroutes handler
  (GET "/" []
    (json-response (transform-todos todo.db/todos)))

  (PUT "/" [name]
    (json-response {"hello" name})))

(def app
  (-> handler
    wrap-json-params))
