export const floatFilter = (data:string):string => {

    return data
        .replace(/[^0-9,.]+/g, '')
        .replace(/(\..*)\./g, '$1');
}
