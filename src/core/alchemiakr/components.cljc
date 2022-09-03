(ns alchemiakr.components
  (:require
   [rum.core :as rum]
   ))


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


(rum/defc check-icon
  []
  [:svg
   {:aria-hidden "true" :fill "currentColor" :view-box "0 0 20 20" :xmlns "http://www.w3.org/2000/svg"}
   [:path {:fill-rule "evenodd" :d "M16.707 5.293a1 1 0 010 1.414l-8 8a1 1 0 01-1.414 0l-4-4a1 1 0 011.414-1.414L8 12.586l7.293-7.293a1 1 0 011.414 0z" :clip-rule "evenodd"}]])


(rum/defc danger-icon
  []
  [:svg
   {:aria-hidden "true" :fill "currentColor" :view-box "0 0 20 20" :xmlns "http://www.w3.org/2000/svg"}
   [:path {:fill-rule "evenodd" :d "M4.293 4.293a1 1 0 011.414 0L10 8.586l4.293-4.293a1 1 0 111.414 1.414L11.414 10l4.293 4.293a1 1 0 01-1.414 1.414L10 11.414l-4.293 4.293a1 1 0 01-1.414-1.414L8.586 10 4.293 5.707a1 1 0 010-1.414z" :clip-rule "evenodd"}]])


(rum/defc warn-icon
  []
  [:svg
   {:aria-hidden "true" :fill "currentColor" :view-box "0 0 20 20" :xmlns "http://www.w3.org/2000/svg"}
   [:path {:fill-rule "evenodd" :d "M8.257 3.099c.765-1.36 2.722-1.36 3.486 0l5.58 9.92c.75 1.334-.213 2.98-1.742 2.98H4.42c-1.53 0-2.493-1.646-1.743-2.98l5.58-9.92zM11 13a1 1 0 11-2 0 1 1 0 012 0zm-1-8a1 1 0 00-1 1v3a1 1 0 002 0V6a1 1 0 00-1-1z"}]])


(rum/defc basic-spinner
  ([]
   (basic-spinner nil))
  ([{:as opts}]
   [:svg
    {:aria-hidden "true"
     :role        "status"
     :class       ["animate-spin"]
     :viewBox     "0 0 100 101"
     :fill        "none"
     :xmlns       "http://www.w3.org/2000/svg"}
    [:path {:class "opacity-25" :fill "currentColor" :d "M100 50.5908C100 78.2051 77.6142 100.591 50 100.591C22.3858 100.591 0 78.2051 0 50.5908C0 22.9766 22.3858 0.59082 50 0.59082C77.6142 0.59082 100 22.9766 100 50.5908ZM9.08144 50.5908C9.08144 73.1895 27.4013 91.5094 50 91.5094C72.5987 91.5094 90.9186 73.1895 90.9186 50.5908C90.9186 27.9921 72.5987 9.67226 50 9.67226C27.4013 9.67226 9.08144 27.9921 9.08144 50.5908Z"}]
    [:path {:fill "currentColor" :d "M93.9676 39.0409C96.393 38.4038 97.8624 35.9116 97.0079 33.5539C95.2932 28.8227 92.871 24.3692 89.8167 20.348C85.8452 15.1192 80.8826 10.7238 75.2124 7.41289C69.5422 4.10194 63.2754 1.94025 56.7698 1.05124C51.7666 0.367541 46.6976 0.446843 41.7345 1.27873C39.2613 1.69328 37.813 4.19778 38.4501 6.62326C39.0873 9.04874 41.5694 10.4717 44.0505 10.1071C47.8511 9.54855 51.7191 9.52689 55.5402 10.0491C60.8642 10.7766 65.9928 12.5457 70.6331 15.2552C75.2735 17.9648 79.3347 21.5619 82.5849 25.841C84.9175 28.9121 86.7997 32.2913 88.1811 35.8758C89.083 38.2158 91.5421 39.6781 93.9676 39.0409Z"}]]))


(rum/defc basic-spinner-bold
  []
  [:svg {:class    ["animate-spin"]
         :xmlns    "http://www.w3.org/2000/svg"
         :fill     "none"
         :view-box "0 0 24 24"}
   [:circle {:class "opacity-25" :cx "12" :cy "12" :r "10" :stroke "currentColor" :stroke-width "4"}]
   [:path {:class "opacity-75" :fill "currentColor" :d "M4 12a8 8 0 018-8V0C5.373 0 0 5.373 0 12h4zm2 5.291A7.962 7.962 0 014 12H0c0 3.042 1.135 5.824 3 7.938l3-2.647z"}]])
