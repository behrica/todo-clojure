(ns todo.run)

(use 'ring.adapter.jetty)
(require '[todo.web :as web])

(defn startTodoApp []
  (run-jetty #'web/app {:port 8080 :join? false :daemon? true})
)
