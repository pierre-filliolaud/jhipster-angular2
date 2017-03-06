export class Trade {
    constructor(
        public id?: string,
        public productType?: string,
        public productFamily?: string,
        public price?: number,
        public currency?: string,
        public creationDate?: any,
    ) {
    }
}
