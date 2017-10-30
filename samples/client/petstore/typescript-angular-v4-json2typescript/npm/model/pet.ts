import {JsonObject, JsonProperty} from 'json2typescript';
import { Category } from './category';
import { Tag } from './tag';


@JsonObject
export class Pet {

    @JsonProperty('id', Number, true)
    id?: number = undefined;

    @JsonProperty('category', Category, true)
    category?: Category = undefined;

    @JsonProperty('name', String)
    name: string = undefined;

    @JsonProperty('photoUrls', [String])
    photoUrls: Array<string> = undefined;

    @JsonProperty('tags', [Tag], true)
    tags?: Array<Tag> = undefined;

        /**
        * pet status in the store
        */
    @JsonProperty('status', Pet.StatusEnum, true)
    status?: Pet.StatusEnum = undefined;

}
export namespace Pet {
        export type StatusEnum = 'available' | 'pending' | 'sold';
        export const StatusEnum = {
                Available: 'available' as StatusEnum,
                Pending: 'pending' as StatusEnum,
                Sold: 'sold' as StatusEnum
        }
}
