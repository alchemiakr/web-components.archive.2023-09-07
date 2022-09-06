(ns page.components
  (:require
   [rum.core :as rum]
   [citrus.core :as citrus]
   [alchemiakr.components :as components]
   #?@(:cljs
       [[oops.core :as oops]
        ["@tanstack/table-core"]
        ["@tanstack/react-table" :as tanstack.rt]])
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


#?(:cljs
   (rum/defc basic-table
     [data columns]
     (let [[state set-state!] (rum/use-state data)
           table              (tanstack.rt/useReactTable
                                #js {:data            state
                                     :columns         columns
                                     :getCoreRowModel (tanstack.rt/getCoreRowModel)})]
       [:table
        [:thead
         (for [header-group (.getHeaderGroups table)]
           [:tr {:key (oops/oget header-group "id")}
            (for [header (oops/oget header-group "headers")]
              [:th {:key   (oops/oget header "id")
                    :scope "col"}
               (when-not (oops/oget header "isPlaceholder")
                 (tanstack.rt/flexRender (oops/oget header "column.columnDef.header") (.getContext header)))])])]
        [:tbody
         (for [row (.. table (getRowModel) -rows)]
           [:tr {:key (oops/oget row "id")}
            (for [cell (.getVisibleCells row)]
              [:td {:key (oops/oget cell "id")}
               (tanstack.rt/flexRender (oops/oget cell "column.columnDef.cell") (.getContext cell))])])]])))


(rum/defc examples-table
  [data columns]
  [:.mt-2.px-2.flex.flex-col.h-screen
   [:.md-table-wrapper
    (basic-table data columns)]
   [:.grid.grid-cols-1.gap-4
    {:class ["md:hidden"]}
    (for [entry data]
      [:.bg-white.p-4.rounded-lg.shadow
       [:.flex.items-center.gap-4
        [:img.rounded-full.profile-image.w-14.h-14
         {:src (:profileUrl entry)}]
        [:.flex.flex-col
         [:.flex.items-baseline
          {:class ["gap-x-0.5"]}
          [:.text-slate-900.text-sm.font-medium (:firstName entry)]
          [:.text-slate-900.text-sm.font-medium (:lastName entry)]
          [:.text-slate-500.text-xs (when-some [age (:age entry)] (str "(" age ")"))]]
         [:flex
          [:span.text-xs.font-medium.text-green-500.bg-green-200.rounded-lg.bg-opacity-50.uppercase
           {:class ["p-1.5"]}
           (:status entry)]]
         #_[:.text-slate-500.text-sm.font-medium ]]]])]]
  #_[:.mt-20.flex.flex-col.h-screen
     [:div
      ;; .-my-2.overflow-x-auto.-mx-4
      {:class ["sm:-mx-6" "lg:-mx-8"]}
      [:.py-2.align-middle.inline-block.min-w-full
       {:class ["sm:px-6" "lg:px-8"]}
       [:.shadow.overflow-auto.border-b.border-gray-200
        ;; .overflow-hidden
        {:class ["sm:rounded-lg"]}
        ]]]])


(rum/defc examples
  []
  [:main
   (section-icon)
   (section-input-fields)])
