(ns degree9.ui.wizard
  "Handles state for building a wizard component."
  (:require [hoplon.core :as h]
            [javelin.core :as j]
            [degree9.debug :as dbg]
            [uikit-hl.button :as button]
            [uikit-hl.card :as card]
            [uikit-hl.flex :as flex]
            [uikit-hl.grid :as grid]
            [uikit-hl.margin :as margin]
            [uikit-hl.modal :as modal]
            [uikit-hl.padding :as padding]
            [uikit-hl.position :as position]
            [uikit-hl.section :as section]
            [uikit-hl.tab :as tab]
            [uikit-hl.text :as text]
            [uikit-hl.utility :as util]
            [uikit-hl.width :as width]))

(dbg/defdebug debug "degree9::experience::wizard")

(defn- current-step [needle haystack]
  (first (keep-indexed #(when (= %2 needle) %1) haystack)))

(h/defelem simple-wizard [{:keys [steps previous next cancel complete header footer] :as attr} kids]
  (let [current   (j/cell 0)
        step      (j/cell= (get steps current))
        id        (:id attr (gensym))]
    (modal/modal :id id
      (modal/dialog attr
        (modal/header
          (modal/title (h/text "~{(:title step (:title attr))}"))
          header)
        (modal/body ::section/muted true ::padding/small true ;::util/overflow-auto true
          (grid/grid ::width/width-1-1 true
            (grid/cell ::width/width-auto true ::util/overflow-auto true
              (tab/tab :left true ::width/child-width-auto true
                (h/for-tpl [[key val] (j/cell= (map-indexed vector steps))]
                  (tab/item ::util/active (j/cell= (= val step))
                    :click #(reset! current @key)
                    (h/a (h/text "~{(:title val)}"))))))
            (grid/cell ::width/width-expand true ::util/overflow-auto true
              (j/cell= (get kids current)))))
        (modal/footer
          (grid/grid :collapse true ::flex/flex true ::flex/middle true
            (grid/cell ::text/left true ::width/width-auto true
              (h/text "~{(str (inc current) \" / \" (count steps))}"))
            (grid/cell ::text/right true ::width/width-expand true
              (button/group ::width/child-width-auto true
                footer
                (h/when-tpl (j/cell= (:cancel? step (:cancel step (:cancel? attr))))
                  (button/button
                    :default true
                    ::modal/close true
                    :disabled (j/cell= (get-in step [:cancel :disabled]))
                    (j/cell= (get-in step [:cancel :text] (get cancel :text cancel)))))
                (h/when-tpl (j/cell= (:previous? step (:previous step (:previous? attr))))
                  (button/button
                    :default true
                    :click (:previous! attr #(swap! current dec))
                    :disabled (j/cell= (get-in step [:previous :disabled]))
                    (j/cell= (get-in step [:previous :text] (get previous :text previous)))))
                (h/when-tpl (j/cell= (:next? step (:next step (:next? attr))))
                  (button/button
                    :default true
                    :click (:next! attr #(swap! current inc))
                    :disabled (j/cell= (get-in step [:next :disabled]))
                    (j/cell= (get-in step [:next :text] (get next :text next)))))
                (h/when-tpl (j/cell= (:complete? step (:complete step (:complete? attr))))
                  (button/button
                    :primary true
                    :click (:submit! attr)
                    :disabled (j/cell= (get-in step [:complete :disabled]))
                    (j/cell= (get-in step [:complete :text] (get complete :text complete)))))))))))))

(h/defelem embedded-wizard [{:keys [title steps previous next cancel complete header footer menu] :as attr} kids]
  (let [current (:current attr (j/cell 0))
        step    (j/cell= (get steps current))
        title   (j/cell= (:title step title))
        attr    (dissoc attr :title :steps :previous :next :cancel :complete :header :footer)]
    (card/card attr
      (h/when-tpl (j/cell= (or header title))
        (card/header
          (h/when-tpl title
            (card/title (h/text "~{title}")))
          header))
      (card/body ::section/muted true ::padding/small true ::util/overflow-auto true
        (grid/grid :match true :small true
          ::margin/remove true
          ::width/width-1-1 true
          ::flex/flex true ::flex/middle true
          (grid/cell ::width/width-1-6 true
            (tab/tab :left true ::width/child-width-auto true
              (h/for-tpl [[key val] (j/cell= (map-indexed vector steps))]
                (tab/item ::util/active (j/cell= (= val step))
                  :click #(reset! current @key)
                  (h/a (h/text "~{(:menu val (:title val))}"))))))
          (grid/cell ::width/width-5-6 true
            (j/cell= (get kids current)))))
      (card/footer
        (grid/grid :collapse true ::flex/flex true ::flex/middle true
          (grid/cell ::text/right true ::width/width-expand true
            (button/group ::width/child-width-auto true
              footer
              (h/when-tpl (j/cell= (:cancel? step (:cancel step (:cancel? attr (:cancel attr)))))
                (button/button
                  :default true
                  :click (:cancel! attr)
                  :disabled (j/cell= (get-in step [:cancel :disabled]))
                  (j/cell= (get-in step [:cancel :text] (get cancel :text cancel)))))
              (h/when-tpl (j/cell= (:previous? step (:previous step (:previous? attr (:previous attr)))))
                (button/button
                  :default true
                  :click (:previous! attr #(swap! current dec))
                  :disabled (j/cell= (get-in step [:previous :disabled]))
                  (j/cell= (get-in step [:previous :text] (get previous :text previous)))))
              (h/when-tpl (j/cell= (:next? step (:next step (:next? attr (:next attr)))))
                (button/button
                  :default true
                  :click (:next! attr #(swap! current inc))
                  :disabled (j/cell= (get-in step [:next :disabled]))
                  (j/cell= (get-in step [:next :text] (get next :text next)))))
              (h/when-tpl (j/cell= (:complete? step (:complete step (:complete? attr (:complete attr)))))
                (button/button
                  :primary true
                  :click (:submit! attr)
                  :disabled (j/cell= (get-in step [:complete :disabled]))
                  (j/cell= (get-in step [:complete :text] (get complete :text complete))))))))))))
