'use client'
import RightArrow from '@image/icons/right.svg'
import LeftArrow from '@image/icons/left.svg'
import RightDoubleArrow from '@image/icons/doubleRight.svg'
import LeftDoubleArrow from '@image/icons/doubleLeft.svg'
import { usePageination } from '@/components/Zustand/pageinationStore'

export default function Pagination() {
  const { dataLength, itemsPerPage, currentPage, setCurrentPage } = usePageination()
  const totalPages = Math.ceil(dataLength / itemsPerPage)
  const startPage = Math.max(currentPage - 2, 1)
  const endPage = Math.min(startPage + 4, totalPages)

  const paginate = (pageNumber: number) => {
    if (pageNumber > totalPages) {
      setCurrentPage(totalPages)
    } else if (pageNumber < 1) {
      setCurrentPage(1)
    } else {
      setCurrentPage(pageNumber)
    }
  }
  return (
    <>
      {dataLength > itemsPerPage && (
        <div className='z-0 mt-4 flex h-12 justify-center'>
          <ul className='pagination flex items-center'>
            {currentPage > 1 && (
              <>
                <li
                  className='cursor-pointer items-center bg-white  px-1  py-1'
                  onClick={() => paginate(currentPage - 5)}
                >
                  <LeftDoubleArrow />
                </li>
                <li
                  className='mx-1 cursor-pointer items-center bg-white  px-1  py-1'
                  onClick={() => paginate(currentPage - 1)}
                >
                  <LeftArrow />
                </li>
              </>
            )}
            {Array(endPage - startPage + 1)
              .fill(0)
              .map((_, i) => (
                <li
                  key={i}
                  className={`mx-1 cursor-pointer px-3 py-1 ${
                    currentPage === startPage + i
                      ? 'rounded-full bg-kyboNavy text-white'
                      : 'bg-white'
                  }`}
                  onClick={() => paginate(startPage + i)}
                >
                  {startPage + i}
                </li>
              ))}
            {currentPage < totalPages && (
              <>
                <li
                  className='mx-1 cursor-pointer items-center bg-white px-1 py-1'
                  onClick={() => paginate(currentPage + 1)}
                >
                  <RightArrow />
                </li>
                <li
                  className='cursor-pointer items-center bg-white px-1 py-1'
                  onClick={() => paginate(currentPage + 5)}
                >
                  <RightDoubleArrow />
                </li>
              </>
            )}
          </ul>
        </div>
      )}
    </>
  )
}
