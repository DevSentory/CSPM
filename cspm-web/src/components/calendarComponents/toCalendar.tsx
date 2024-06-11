'use client'
import { ko } from 'date-fns/locale/ko'
import React, { useRef, useState } from 'react'
import DatePicker, { registerLocale } from 'react-datepicker'
import { useCalendarPicker } from '@/components/Zustand/store'
registerLocale('ko', ko)
import Calendar from '@image/icons/calendarIcon.svg'
import 'react-datepicker/dist/react-datepicker.css'

export default function ToCalendar() {
  const datePickerRef = useRef<DatePicker>(null)
  const { toPickDate, setToPickDate } = useCalendarPicker()
  const handleToPickDate = (date: Date | null) => {
    setToPickDate(date)
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
        id='toPickDate'
        name='toPickDate'
        ref={datePickerRef}
        selected={toPickDate}
        showTimeSelect
        onChange={(date) => handleToPickDate(date)}
        locale={ko}
        dateFormat='yyyy/MM/d H:mm'
        isClearable
      />
    </div>
  )
}
