import {JsonObject, JsonProperty} from 'json2typescript';


@JsonObject
export class Order {

    @JsonProperty('id', Number, true)
    id?: number = undefined;

    @JsonProperty('petId', Number, true)
    petId?: number = undefined;

    @JsonProperty('quantity', Number, true)
    quantity?: number = undefined;

    @JsonProperty('shipDate', Date, true)
    shipDate?: Date = undefined;

        /**
        * Order Status
        */
    @JsonProperty('status', Order.StatusEnum, true)
    status?: Order.StatusEnum = undefined;

    @JsonProperty('complete', Boolean, true)
    complete?: boolean = undefined;

}
export namespace Order {
        export type StatusEnum = 'placed' | 'approved' | 'delivered';
        export const StatusEnum = {
                Placed: 'placed' as StatusEnum,
                Approved: 'approved' as StatusEnum,
                Delivered: 'delivered' as StatusEnum
        }
}
