(ns page.index.table
  (:require
   ["@tanstack/table-core"]
   ["@tanstack/react-table" :as tanstack.rt]
   ))


(def sample-data
  #js [
       {
        :firstName  "tanner",
        :lastName   "linsley",
        :age        24,
        :visits     100,
        :status     "In Relationship",
        :progress   50,
        :profileUrl "https://i.imgur.com/uZpuyiV.png"
        }
       {
        :firstName  "tandy",
        :lastName   "miller",
        :age        40,
        :visits     40,
        :status     "Single",
        :progress   80,
        :profileUrl "https://i.imgur.com/Ap4unoG.jpg"
        }
       {
        :firstName  "joe",
        :lastName   "dirte",
        :age        45,
        :visits     20,
        :status     "Complicated",
        :progress   10,
        :profileUrl "https://i.imgur.com/DNebsKC.jpg"
        }
       ])


(def column-helper (tanstack.rt/createColumnHelper))


(def columns
  #js [
       (. column-helper accessor #(:firstName %) #js {:id "firstName" :cell #(.getValue %)})
       (. column-helper accessor #(:lastName %) #js {:id "lastName" :cell #(.getValue %)})
       (. column-helper accessor #(:age %) #js {:id "age" :cell #(.getValue %)})
       (. column-helper accessor #(:visits %) #js {:id "visits" :cell #(.getValue %)})
       (. column-helper accessor #(:status %) #js {:id "status" :cell #(.getValue %)})
       (. column-helper accessor #(:progress %) #js {:id "progress" :cell #(.getValue %)})
       ])
