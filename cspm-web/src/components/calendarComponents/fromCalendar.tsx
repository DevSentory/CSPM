'use client'
import { ko } from 'date-fns/locale/ko'
import React, { useRef } from 'react'
import DatePicker, { registerLocale } from 'react-datepicker'
import Calendar from '@image/icons/calendarIcon.svg'
import { useCalendarPicker } from '@/components/Zustand/store'
registerLocale('ko', ko)

import 'react-datepicker/dist/react-datepicker.css'

export default function FromCalendar() {
  const datePickerRef = useRef<DatePicker>(null)
  const { fromPickDate, setFromPickDate } = useCalendarPicker()

  const handleFromPickDate = (date: Date | null) => {
    setFromPickDate(date)
  }

  const handleIconClick = () => {
    datePickerRef.current?.setFocus()
  }

  const CalendarIcon: React.FC<{ onClick: () => void }> = ({ onClick }) => (
    <div className='mr-4 h-4 w-4' onClick={onClick}>
      <Calendar />
    </div>
  )
  return (
    <div className='date-picker-wrapper flex'>
      <CalendarIcon onClick={handleIconClick} />
      <DatePicker
        aria-label='시작 날짜 선택'
        id='fromPickDate'
        name='fromPickDate'
        ref={datePickerRef}
        selected={fromPickDate}
        showTimeSelect
        onChange={(date) => {
          handleFromPickDate(date)
        }}
        locale={ko}
        dateFormat='yyyy/MM/d H:mm'
        isClearable
        showDisabledMonthNavigation
      />
    </div>
  )
}
