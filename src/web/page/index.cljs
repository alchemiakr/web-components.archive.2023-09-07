(ns page.index
  (:require
   [oops.core :as oops]
   [goog.events :as gevents]
   [goog.dom]
   [goog.dom.classlist :as classlist]
   [reitit.core :as reitit]
   [reitit.frontend :as rf]
   [reitit.frontend.easy :as rfe]
   [reitit.frontend.history :as rfh]
   [taoensso.timbre :as timbre]
   [reitit.ring]
   [rum.core :as rum]
   [citrus.core :as citrus]
   [page.components]
   [page.index.table :as table]
   ))


(defn render
  [component node dev?]
  (if dev?
    (rum/mount component node)
    (if (seq (goog.dom/getChildren node))
      (rum/hydrate component node)
      (rum/mount component node))))


(defn system-dark-mode?
  []
  (or (= (oops/oget js/localStorage "?theme") "dark")
      (and (not (js-in "theme" js/localStorage))
           (and (js-fn? js/window.matchMedia)
                (.-matches (js/window.matchMedia "(prefers-color-scheme: dark)"))))))


(defmulti theme-controller (fn [event] event))
(defmulti user-state-controller (fn [event] event))


(defmethod user-state-controller :init
  []
  (timbre/debug :user-state :init)
  {})


(defmethod user-state-controller :navigate [_ [navi-state] state]
  (timbre/debug :user-state :navigate (get-in navi-state [::reitit/match :path]))
  (let [new-state (merge state navi-state)]
    {:state       new-state
     :on-navigate new-state}))


(defmethod theme-controller :default
  [e [selector]]
  {:state     nil
   :set-theme {:theme    e
               :selector selector}})


(defonce reconciler
  (citrus/reconciler
    {:state           (atom {})
     :controllers     {:user-state user-state-controller
                       :theme      theme-controller}
     :effect-handlers {:on-navigate
                       (fn [r _controller-name {:keys [::reitit/match] :as effect}]
                         (let [on-navi (get-in match [:data :on-navigate])]
                           (when (fn? on-navi)
                             (on-navi (assoc effect :dev/after-load (:dev/after-load effect)) r))))
                       :set-theme
                       (fn [_r _controller-name {:keys [theme selector]}]
                         (let [el (or (js/document.querySelector selector) js/document.documentElement)]
                           (case theme
                             :light  (do
                                       (oops/oset! js/localStorage "!theme" "light")
                                       (classlist/enable el "light" true)
                                       (classlist/enable el "dark" false))
                             :dark   (do
                                       (oops/oset! js/localStorage "!theme" "light")
                                       (classlist/enable el "light" false)
                                       (classlist/enable el "dark" true))
                             :system (do
                                       (js-delete js/localStorage "theme")
                                       (let [dark-mode? (system-dark-mode?)]
                                         (classlist/enable el "light" (not dark-mode?))
                                         (classlist/enable el "dark" dark-mode?)))
                             nil)))}}))


(def router
  (rf/router
    [["/"
      {:name ::root
       :on-navigate
       (fn [{dev? :dev/after-load :as _user-state} _reconciler]
         (render (page.components/header reconciler) (js/document.querySelector "#header") dev?)
         (render (page.components/examples) (js/document.querySelector "#mount") dev?))}]
     ["/table/"
      {:name ::table
       :on-navigate
       (fn [{dev? :dev/after-load :as _user-state} _reconciler]
         (render (page.components/examples-table table/sample-data table/columns) (js/document.querySelector "main") dev?))}]]))


;;


(defn ^:dev/before-load before-load
  []
  (timbre/debug ::before-load))


(defn ^:dev/after-load after-load
  []
  (timbre/debug ::after-load)
  (citrus/dispatch!
    reconciler :user-state :navigate
    {:dev/after-load true}))


(defn ^:export unload
  [e]
  (timbre/debug ::unload e))


(defn ^:export onload
  [e]
  (timbre/debug ::onload e)
  (rfe/start!
    router
    (fn
      [match html-history]
      (timbre/debug match html-history)
      (citrus/dispatch-sync!
        reconciler :user-state :navigate
        {::reitit/match  match
         ::reitit/router router
         :heml-history   html-history}))
    {:use-fragment false})
  (when-some [history @rfe/history]
    ;; unlisten POPSTATE, CLICK handler
    (rfh/-stop history)))


(defn ^:export init!
  []
  (timbre/swap-config!
    update :middleware conj (fn [data] (-> data (assoc :raw-console? true))))


  #_(citrus/broadcast-sync! reconciler :init)
  (citrus/dispatch-sync! reconciler :user-state :init)


  (when (some? goog/global.document)
    (gevents/listenOnce goog.global goog.events.EventType.LOAD onload)
    (gevents/listen goog.global goog.events.EventType.BEFOREUNLOAD unload)))


(comment
  )
