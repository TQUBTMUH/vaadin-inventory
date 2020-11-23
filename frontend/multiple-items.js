import {html, PolymerElement} from '@polymer/polymer/polymer-element.js';
import '@vaadin/vaadin-form-layout/src/vaadin-form-layout.js';
import '@vaadin/vaadin-date-picker/src/vaadin-date-picker.js';
import '@vaadin/vaadin-select/src/vaadin-select.js';
import '@vaadin/vaadin-list-box/src/vaadin-list-box.js';
import '@vaadin/vaadin-item/src/vaadin-item.js';
import '@vaadin/vaadin-ordered-layout/src/vaadin-vertical-layout.js';
import '@vaadin/vaadin-ordered-layout/src/vaadin-horizontal-layout.js';
import '@vaadin/vaadin-grid/src/vaadin-grid.js';
import '@vaadin/vaadin-grid/src/vaadin-grid-selection-column.js';
import '@vaadin/vaadin-grid/src/vaadin-grid-column.js';
import '@vaadin/vaadin-text-field/src/vaadin-integer-field.js';
import '@vaadin/vaadin-button/src/vaadin-button.js';

class MultipleItems extends PolymerElement {

    static get template() {
        return html`
<style include="shared-styles">
                :host {
                    display: block;
                    height: 100%;
                }
            </style>
<vaadin-vertical-layout style="width: 100%; height: 100%; padding: var(--lumo-space-xl);">
 <vaadin-form-layout></vaadin-form-layout>
 <label>Date</label>
 <vaadin-date-picker></vaadin-date-picker>
 <label>Supplier</label>
 <vaadin-select value="Item one">
  <template>
   <vaadin-list-box>
    <vaadin-item>
      Item one 
    </vaadin-item>
    <vaadin-item>
      Item two 
    </vaadin-item>
    <vaadin-item>
      Item three 
    </vaadin-item>
   </vaadin-list-box>
  </template>
 </vaadin-select>
 <vaadin-horizontal-layout theme="spacing" style="width: 40%;">
  <vaadin-grid items="[[items]]" id="vaadinGrid" style="flex-grow: 0; flex-shrink: 0; width: 100%;">
   <vaadin-grid-selection-column auto-select=""></vaadin-grid-selection-column>
   <vaadin-grid-column width="50px" flex-grow="0">
    <template class="header">
      # 
    </template>
    <template>
      [[index]] 
    </template>
    <template class="footer">
      # 
    </template>
   </vaadin-grid-column>
   <vaadin-grid-column>
    <template class="header">
      Value 
    </template>
    <template>
      [[item.value]] 
    </template>
    <template class="footer">
      Value 
    </template>
   </vaadin-grid-column>
  </vaadin-grid>
  <vaadin-form-layout style="flex-shrink: 0; width: 100%;">
   <vaadin-select value="Item one">
    <template>
     <vaadin-list-box>
      <vaadin-item selected>
        Item one 
      </vaadin-item>
      <vaadin-item>
        Item two 
      </vaadin-item>
      <vaadin-item>
        Item three 
      </vaadin-item>
     </vaadin-list-box>
    </template>
   </vaadin-select>
   <vaadin-integer-field></vaadin-integer-field>
   <vaadin-button theme="primary">
    Add Item
   </vaadin-button>
  </vaadin-form-layout>
 </vaadin-horizontal-layout>
 <vaadin-button theme="primary">
  Save
 </vaadin-button>
</vaadin-vertical-layout>
`;
    }

    static get is() {
        return 'multiple-items';
    }

    static get properties() {
        return {
            // Declare your properties here.
        };
    }
}

customElements.define(MultipleItems.is, MultipleItems);
