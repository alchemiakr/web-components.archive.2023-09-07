(ns page.components
  (:require
   [rum.core :as rum]
   [citrus.core :as citrus]
   [alchemiakr.components :as components]
   ))


(rum/defc code-example
  []
  [:.code-example
   [:.code-example-control]
   [:.code-preview-wrapper
    [:.code-preview
     [:.code-reponsive-wrapper
      [:iframe]]]]])


;;


(rum/defc header
  [r]
  [:header.relative
   [:ul#ui-listbox-option-theme
    [:li
     {:on-click #(citrus/dispatch! r :theme :light)}
     [:i.material-icons.mr-2 "light_mode"]
     "라이트"]
    [:li
     {:on-click #(citrus/dispatch! r :theme :dark)}
     [:i.material-icons.mr-2 "dark_mode"]
     "다크"]
    [:li
     {:on-click #(citrus/dispatch! r :theme :system)}
     [:i.material-icons.mr-2 "computer"]
     "시스템"]]])


(rum/defc section-icon
  []
  [:section.p-4
   [:.flex
    [:.inline-flex.flex-shrink-0.justify-center.items-center.w-8.h-8.rounded-lg [:span.w-5.h-5 (components/check-icon)]]
    [:.inline-flex.flex-shrink-0.justify-center.items-center.w-8.h-8.rounded-lg.text-green-500.bg-green-100 {:class "dark:text-green-200 dark:bg-green-800"} [:span.w-5.h-5 (components/check-icon)]]
    [:.inline-flex.flex-shrink-0.justify-center.items-center.w-8.h-8.rounded-lg.text-yellow-500.bg-yellow-100 [:span.w-5.h-5 (components/check-icon)]]
    ]
   [:.flex
    [:.inline-flex.flex-shrink-0.justify-center.items-center.w-8.h-8.rounded-lg [:span.w-5.h-5 (components/danger-icon)]]
    [:.inline-flex.flex-shrink-0.justify-center.items-center.w-8.h-8.rounded-lg.text-red-500.bg-red-100 {:class "dark:text-red-200 dark:bg-red-800"} [:span.w-5.h-5 (components/danger-icon)]]
    [:.inline-flex.flex-shrink-0.justify-center.items-center.w-8.h-8.rounded-lg [:span.w-5.h-5 (components/danger-icon)]]]
   [:.flex
    [:.inline-flex.flex-shrink-0.justify-center.items-center.w-8.h-8.rounded-lg [:span.w-5.h-5 (components/warn-icon)]]
    [:.inline-flex.flex-shrink-0.justify-center.items-center.w-8.h-8.rounded-lg.text-orange-500.bg-orange-100 {:class "dark:text-orange-200 dark:bg-orange-800"} [:span.w-5.h-5 (components/warn-icon)]]
    [:.inline-flex.flex-shrink-0.justify-center.items-center.w-8.h-8.rounded-lg.text-red-500.bg-red-100 [:span.w-5.h-5 (components/warn-icon)]]]
   [:.flex
    [:.inline-flex.flex-shrink-0.justify-center.items-center.w-8.h-8.rounded-lg.text-green-500 {:class "dark:text-green-200"} [:span.w-5.h-5 (components/basic-spinner-bold)]]
    [:.inline-flex.flex-shrink-0.justify-center.items-center.w-8.h-8.rounded-lg.text-blue-500 {:class "dark:text-blue-200"} [:span.w-5.h-5 (components/basic-spinner-bold)]]
    [:.inline-flex.flex-shrink-0.justify-center.items-center.w-8.h-8.rounded-lg.text-yellow-500 {:class "dark:text-yellow-200"} [:span.w-5.h-5 (components/basic-spinner-bold)]]
    ]
   [:.flex
    [:.inline-flex.flex-shrink-0.justify-center.items-center.w-8.h-8.rounded-lg.text-green-500 {:class "dark:text-green-200"} [:span.w-5.h-5 (components/basic-spinner)]]
    [:.inline-flex.flex-shrink-0.justify-center.items-center.w-8.h-8.rounded-lg.text-blue-500 {:class "dark:text-blue-200"} [:span.w-5.h-5 (components/basic-spinner)]]
    [:.inline-flex.flex-shrink-0.justify-center.items-center.w-8.h-8.rounded-lg.text-yellow-500 {:class "dark:text-yellow-200"} [:span.w-5.h-5 (components/basic-spinner)]]
    ]])


(rum/defc section-input-fields
  []
  [:section.p-4
   [:.floating-label-input
    [:input
     {:id          "floating-label-input-01"
      :type        "text"
      :placeholder "Placeholder"}]
    [:label {:for "floating-label-input-01"}]]])


(rum/defc examples
  []
  [:main
   (section-icon)
   (section-input-fields)])
