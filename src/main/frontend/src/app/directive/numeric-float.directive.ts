import {Directive, ElementRef, HostListener} from "@angular/core";

@Directive({
    selector: '[floatFilter]'
})
export class NumericFloatDirective {

    private floatRegExp:RegExp = new RegExp(/[^0-9,.]+/g);
    private filterPointRegExp:RegExp = new RegExp(/(\..*)\./g);

    constructor(private element:ElementRef) {}

    @HostListener('input')
    onKeyDown(){
        const input = this.element.nativeElement;
        input.value = input.value
            .replace(this.floatRegExp, '')
            .replace(this.filterPointRegExp, "$1");
    }
}
