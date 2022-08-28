(ns hooks
  (:require
   [clojure.java.io :as jio]
   [clojure.data.json :as json]
   [runner.shadow-cljs :as env.shadow-cljs]
   [runner.web.server-render :as server-render]
   [env]
   )
  (:import
   java.time.Instant
   ))


(defn direnv
  [{:keys [:shadow.build/mode]}]
  (as-> (case mode
          :release env/release
          env/dev)
    $
    (do
      (jio/make-parents (jio/file (:server-render/body-script-modules-file $)))
      $)))


(defn copy-manifest-file
  {:shadow.build/stage :flush}
  [state]
  (let [manifest-name (get-in state [:shadow.build/config :build-options :manifest-name])]
    (jio/copy
      (jio/file (get-in state [:shadow.build/config :output-dir]) manifest-name)
      (jio/file (:server-render/root-dir (direnv state)) manifest-name)))
  state)


(defn- spit-body-script-modules-1
  [state]
  (with-open [w (jio/writer (jio/file (:server-render/body-script-modules-file (direnv state))))]
    (binding [*out* w]
      (prn (server-render/transitive-body-script-modules
             (env.shadow-cljs/transitive-modules
               #_(vals (:shadow.build.modules/config state))
               (json/read
                 (jio/reader
                   (jio/file
                     (get-in state [:shadow.build/config :output-dir])
                     (get-in state [:shadow.build/config :build-options :manifest-name])))
                 :key-fn keyword)))))))


(defn spit-body-script-modules
  {:shadow.build/stage :flush}
  [state]
  (spit-body-script-modules-1 state)
  state)


(defn inspect-state
  {:shadow.build/stages #{}}
  [state]
  (prn (keys state))
  (prn "Config" (:shadow.build.modules/config state))
  (prn "Modules" (:shadow.build.modules/modules state))
  (prn "Modules-Order" (:shadow.build.modules/module-order state))
  (prn "Build-Config" (:shadow.build/config state))
  (prn (json/read (jio/reader (jio/file (:web/file-public-root (direnv state)) "manifest.json")) :key-fn keyword))
  state)
