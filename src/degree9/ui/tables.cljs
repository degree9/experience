(ns degree9.ui.tables
  (:require [hoplon.core :as h]
            [javelin.core :as j]
            [uikit-hl.button :as button]
            [uikit-hl.table :as table]))

(h/defelem table [{:keys [button headers cells data] :as attr} kids]
  (table/table (dissoc attr :headers)
    (table/header
      (table/row
        (h/for-tpl [h headers]
          (table/hcell h))
        (table/hcell ::table/shrink true)))
    (table/body
      (h/for-tpl [d data]
        (table/row
          (h/for-tpl [c cells]
            (table/cell (h/text "~{(c d)}")))
          (h/when-tpl button
            (let [{:keys [click label] :as attr} button]
              (table/cell
                (button/button
                  (dissoc attr :click)
                  :click (partial click @d)
                  label)))))))))
