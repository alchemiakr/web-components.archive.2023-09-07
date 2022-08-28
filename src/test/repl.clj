(ns repl
  (:require
   [clojure.java.io :as jio]
   [clojure.stacktrace :as stacktrace]
   [clojure.tools.namespace.find :as t.n.f]
   [hawk.core :as hawk]
   [ns-tracker.core :as nt]
   ))


(defonce modified-namespaces (nt/ns-tracker ["src/core" "src/web"]))


(defn live-update-fns
  ""
  [dirs]
  (transduce
    (comp
      (filter (fn [sym] (try (the-ns sym) (catch Throwable _ false))))
      (map the-ns)
      (map ns-publics)
      cat
      (filter (fn [[_ v]] (:repl/live-update (meta v))))
      (map val)
      (map (juxt identity var-get))
      (filter (fn [[_ o]] (fn? o))))
    conj
    []
    (into #{} (t.n.f/find-namespaces (map jio/as-file dirs)))))


(defn live-update!
  [_e]
  (let [modified-nses (modified-namespaces)]
    (println "Modified Namespaces:" modified-nses)
    (doseq [ns-sym modified-nses]
      (try
        (require ns-sym :reload)
        (catch Exception e
          (stacktrace/print-stack-trace e 20))))
    (doseq [[v f] (live-update-fns ["src/web"])]
      (try
        (println v)
        (f)
        (catch Exception e
          (stacktrace/print-stack-trace e 5))))))


(defonce watch
  (doto (hawk/watch!
          [{:paths   ["src/core" "src/web"]
            :filter  (fn [ctx e]
                       (and (hawk/file? ctx e)
                            (or (hawk/modified? ctx e)
                                (hawk/created? ctx e))))
            :handler (fn [_ctx e]
                       (live-update! e))}])
    (prn)))


(comment
  (hawk/stop! watch)
  (live-update! nil)
  )
