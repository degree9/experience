(ns degree9.ui.wizard
  "Handles state for building a wizard component."
  (:require [hoplon.core :as h]
            [javelin.core :as j]
            [degree9.debug :as dbg]
            [uikit-hl.button :as button]
            [uikit-hl.card :as card]
            [uikit-hl.flex :as flex]
            [uikit-hl.grid :as grid]
            [uikit-hl.height :as height]
            [uikit-hl.margin :as margin]
            [uikit-hl.modal :as modal]
            [uikit-hl.padding :as padding]
            [uikit-hl.position :as position]
            [uikit-hl.section :as section]
            [uikit-hl.tab :as tab]
            [uikit-hl.text :as text]
            [uikit-hl.utility :as util]
            [uikit-hl.width :as width]))

(dbg/defdebug debug "degree9:experience:wizard")

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

(h/defelem embedded-header [{:keys [title] :as attr} kids]
  (card/header
    (grid/grid :collapse true
      (h/when-tpl title
        (grid/cell ::text/left true ::width/width-expand true
          (card/title (h/text "~{title}"))))
      (for [k kids] (grid/cell k)))))

(h/defelem embedded-menu [{:keys [menu current] :as attr} kids]
  (grid/cell ::width/width-auto true
    (tab/tab :left true ::width/child-width-auto true
      (h/for-tpl [[idx page] menu]
        (j/cell-let [{:keys [icon label]} page]
          (tab/item :click #(reset! current @idx)
            ::util/active (j/cell= (= current idx))
            (h/a (h/i ::margin/small-right true :class (j/cell= [:fal icon])) (h/span ::margin/remove true label))))))))

(h/defelem embedded-body [{:keys [menu current] :as attr} kids]
  (card/body ::section/muted true ::padding/small true ::util/overflow-auto true
    (grid/grid :match true :small true
      ::margin/remove true
      ::width/width-1-1 true
      ::flex/flex true ::flex/middle true
      (h/when-tpl menu
        (embedded-menu :current current :menu menu))
      (grid/cell ::width/width-expand true ::util/overflow-auto true kids))))

(h/defelem embedded-footer [{:keys [cancel previous next complete] :as attr} kids]
  (card/footer
    (grid/grid :collapse true ::flex/flex true ::flex/middle true
      kids
      (grid/cell ::text/right true ::width/width-expand true
        (button/group ::width/child-width-auto true
          (h/when-tpl cancel
            (j/cell-let [{:keys [click disabled label]} cancel]
              (button/button :default true :click click :disabled disabled (h/text "~{(or label \"Cancel\")}"))))
          (h/when-tpl previous
            (j/cell-let [{:keys [click disabled label]} previous]
              (button/button :default true :click click :disabled disabled (h/text "~{(or label \"Previous\")}"))))
          (h/when-tpl next
            (j/cell-let [{:keys [click disabled label]} next]
              (button/button :default true :click click :disabled disabled (h/text "~{(or label \"Next\")}"))))
          (h/when-tpl complete
            (j/cell-let [{:keys [click disabled label]} complete]
              (button/button :primary true :click click :disabled disabled (h/text "~{(or label \"Complete\")}")))))))))

(h/defelem embedded [{:keys [current pages] :or {current (j/cell ::default) pages {}} :as attr} kids]
  (let [page     (j/cell= (get pages current))
        header   (j/cell= (:header page (:header attr [])))
        body     (j/cell= (not (empty? kids)))
        footer   (j/cell= (:footer page (:footer attr [])))
        menu     (j/cell= (:menu page (:menu attr [])))
        title    (j/cell= (:title page (:title attr)))
        cancel   (j/cell= (:cancel page (:cancel attr)))
        previous (j/cell= (:previous page (:previous attr)))
        next     (j/cell= (:next page (:next attr)))
        complete (j/cell= (:complete page (:complete attr)))]
    (card/card
      (dissoc attr :header :footer :menu :current :pages :title :previous :next :cancel :complete)
      (h/when-tpl header
        (embedded-header :title title header))
      (h/when-tpl body
        (embedded-body :menu menu :current current
          ::height/viewport "expand: true"
          kids))
      (h/when-tpl footer
        (embedded-footer :cancel cancel :previous previous :next next :complete complete footer)))))
