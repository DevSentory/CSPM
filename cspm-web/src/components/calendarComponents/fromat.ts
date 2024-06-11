import { format } from 'date-fns'

export default function Format(date: Date | null) {
  if (date != null) {
    return format(date, 'yyyy-MM-dd HH:mm')
  }
  return ''
}
