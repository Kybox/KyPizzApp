import {Directive, ElementRef, HostListener} from "@angular/core";

@Directive({
    selector: '[integerFilter]'
})
export class NumericIntegerFilter {

    private integerRegExp:RegExp = new RegExp(/[^\d]/g);
    constructor(private element:ElementRef) {}

    @HostListener('input')
    onKeyDown() {
        const input = this.element.nativeElement;
        input.value = input.value.replace(this.integerRegExp, '');
    }
}
