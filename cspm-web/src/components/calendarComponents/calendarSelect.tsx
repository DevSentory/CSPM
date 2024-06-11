import FromCalendar from '@/components/calendarComponents/fromCalendar'
import ToCalendar from '@/components/calendarComponents/toCalendar'

export default function CalendarSelect() {
  return (
    <>
      <div className='flex h-10 w-full items-center justify-between border-2 bg-white text-black '>
        <span className='flex h-full w-[20%] items-center justify-center  bg-gray-200'>
          조회기간
        </span>
        <div className='flex h-full w-[80%] items-center justify-center'>
          <div className='flex h-full w-[45%] items-center justify-center overflow-x-hidden '>
            <FromCalendar />
          </div>
          <span className='flex w-[10%] items-center justify-center'>~</span>
          <div className='flex h-full w-[45%] items-center justify-center overflow-x-hidden'>
            <ToCalendar />
          </div>
        </div>
      </div>
    </>
  )
}
