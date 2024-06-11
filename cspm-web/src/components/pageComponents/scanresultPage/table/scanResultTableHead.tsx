import React from 'react'

interface TableHeadersProps {
  toggleAllCheckboxes: () => void
  allChecked: boolean
}

export default function TableHeaders({ toggleAllCheckboxes, allChecked }: TableHeadersProps) {
  return (
    <div className='grid h-12 w-full grid-cols-21 items-center gap-2 border-b-2 border-t-2 border-gray-300 bg-gray-200'>
      <div className='col-span-1 flex justify-center overflow-hidden'>
        <input type='checkbox' checked={allChecked} onChange={toggleAllCheckboxes} />
      </div>
      <div className='col-span-2 flex justify-center overflow-hidden text-nowrap'>스캔 날짜</div>
      <div className='col-span-1 flex justify-center overflow-hidden text-nowrap'>RID</div>
      <div className='col-span-3 flex justify-center overflow-hidden text-center'>
        <span className='w-2/5'>
          취약점
          <br />
          상태
        </span>
        <span className='w-3/5'>
          Account
          <br />
          Name
        </span>
      </div>
      <div className='col-span-2 flex justify-center overflow-hidden text-nowrap'>Account ID</div>
      <div className='col-span-3 flex justify-center overflow-hidden text-nowrap'>Resource ID</div>
      <div className='col-span-1 flex justify-center overflow-hidden text-nowrap'>고객사</div>
      <div className='col-span-1 flex justify-center overflow-hidden text-nowrap'>취약 등급</div>
      <div className='col-span-3 flex justify-center overflow-hidden text-nowrap'>취약점이름</div>
      <div className='col-span-3 flex justify-center overflow-hidden text-nowrap'>Compliance</div>
      <div className='col-span-1 flex justify-center overflow-hidden '>상세보기</div>
    </div>
  )
}
