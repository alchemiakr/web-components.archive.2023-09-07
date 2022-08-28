(ns alchemiakr.components)

(def light-mode-icon
  [:svg {:view-box        "0 0 24 24"
         :fill            "none"
         :stroke-width    "2"
         :stroke-linecap  "round"
         :stroke-linejoin "round"
         :class           "w-6 h-6"}
   [:path {:d     "M15 12a3 3 0 1 1-6 0 3 3 0 0 1 6 0Z"
           :class "fill-sky-400/20 stroke-sky-500"}]
   [:path {:d     "M12 4v1M17.66 6.344l-.828.828M20.005 12.004h-1M17.66 17.664l-.828-.828M12 20.01V19M6.34 17.664l.835-.836M3.995 12.004h1.01M6 6l.835.836"
           :class "stroke-sky-500"}]])


(def check-icon [:svg {:aria-hidden "true" :class "w-5 h-5" :fill "currentColor" :view-box "0 0 20 20" :xmlns "http://www.w3.org/2000/svg"} [:path {:fill-rule "evenodd" :d "M16.707 5.293a1 1 0 010 1.414l-8 8a1 1 0 01-1.414 0l-4-4a1 1 0 011.414-1.414L8 12.586l7.293-7.293a1 1 0 011.414 0z" :clip-rule "evenodd"}]])


(def danger-icon [:svg {:aria-hidden "true" :class "w-5 h-5" :fill "currentColor" :view-box "0 0 20 20" :xmlns "http://www.w3.org/2000/svg"} [:path {:fill-rule "evenodd" :d "M4.293 4.293a1 1 0 011.414 0L10 8.586l4.293-4.293a1 1 0 111.414 1.414L11.414 10l4.293 4.293a1 1 0 01-1.414 1.414L10 11.414l-4.293 4.293a1 1 0 01-1.414-1.414L8.586 10 4.293 5.707a1 1 0 010-1.414z" :clip-rule "evenodd"}]])


(def warn-icon [:svg {:aria-hidden "true" :class "w-5 h-5" :fill "currentColor" :view-box "0 0 20 20" :xmlns "http://www.w3.org/2000/svg"} [:path {:fill-rule "evenodd" :d "M8.257 3.099c.765-1.36 2.722-1.36 3.486 0l5.58 9.92c.75 1.334-.213 2.98-1.742 2.98H4.42c-1.53 0-2.493-1.646-1.743-2.98l5.58-9.92zM11 13a1 1 0 11-2 0 1 1 0 012 0zm-1-8a1 1 0 00-1 1v3a1 1 0 002 0V6a1 1 0 00-1-1z"}]])


(def basic-spinner
  [:svg {:class    "animate-spin h-5 w-5"
         :xmlns    "http://www.w3.org/2000/svg"
         :fill     "none"
         :view-box "0 0 24 24"}
   [:circle {:class "opacity-25" :cx "12" :cy "12" :r "10" :stroke "currentColor" :stroke-width "4"}]
   [:path {:class "opacity-75" :fill "currentColor" :d "M4 12a8 8 0 018-8V0C5.373 0 0 5.373 0 12h4zm2 5.291A7.962 7.962 0 014 12H0c0 3.042 1.135 5.824 3 7.938l3-2.647z"}]])
