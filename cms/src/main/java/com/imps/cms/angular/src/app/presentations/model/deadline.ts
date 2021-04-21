import {Conference} from "./conference";
import {DeadlineType} from "./deadlineType";

export class Deadline{
  id: number
  conference: Conference
  date: Date
  deadlineType: DeadlineType
}
